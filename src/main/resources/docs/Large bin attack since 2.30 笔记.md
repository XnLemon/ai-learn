## Large bin attack since 2.30 笔记

### Large Bin Attack

#### 利用范围

Large Bin Attack 是一种较为困难的攻击方式，他对攻击的条件要求较多，实现也较为复杂，通常和 Unsorted Bin 打配合来实现 house of storm，来达到提升影响力的作用。

**glibc2.29 及以下版本，可以利用 Large Bin Attack 来写两个地址。**

**但是在 glibc2.30 及以上版本中，只能利用 Large Bin Attack 来写一个地址。**

### 背景

![image-20240412115733077](C:\Users\16040\AppData\Roaming\Typora\typora-user-images\image-20240412115733077.png)

glibc2.30之后 在largebin的插入时候加了两个检查

```c
Check 1 : 
>    if (__glibc_unlikely (fwd->bk_nextsize->fd_nextsize != fwd))
>        malloc_printerr ("malloc(): largebin double linked list corrupted (nextsize)");
Check 2 : 
>    if (bck->fd != fwd)
>        malloc_printerr ("malloc(): largebin double linked list corrupted (bk)");
```

增加了对双向链表完整性的检查，这样的检查方式正如同 glibc2.29 中增加的对 unsorted bin 类似。但是与其不同的是，这个检查只存在于插入的 unsorted chunk size 大于 chunk 时候，也就是说，如果我们构造一个小于所有 largebin 中堆块的 unsorted chunk，那么就可以成功利用上面那个分支操作。

#### 查看target的地址及值

我们使用pwndbg调试程序 由于加了-g参数 所以可以直接通过源码下断点在line43处

![image-20240412120056459](C:\Users\16040\AppData\Roaming\Typora\typora-user-images\image-20240412120056459.png)

可以看到稍后要overwrite的target变量地址在`0x7fffffffdc00`处，target变量值为`0`

#### 查看已创建的chunk

再次下断点再line56处 这段代码申请了`chunk_p1,size=0x428`，`chunk_g1,size=0x18`，`chunk_p2,size=0x418`，`chunk_g2,size=0x18`四个堆块，其中chunk_g1，chunk_g2起到了在free(chunk_p1)或free(chunk_p2)后不会使其与附近堆块合并的作用，然后我们可以看一下现在堆的情况。

![image-20240412121010983](C:\Users\16040\AppData\Roaming\Typora\typora-user-images\image-20240412121010983.png)

![image-20240412121048084](C:\Users\16040\AppData\Roaming\Typora\typora-user-images\image-20240412121048084.png)

之后再下断点至line63，这里free掉了size更大的p1

我们可以看到在经过`free(p1)`后，又申请了一个`g3 = malloc(0x438)`，并且使得chunkp1进入了largebin中

而这个chunk是比一开始的p1chunk要更大的 目前的chunk情况是这样的
![image-20240425194912882](C:\Users\16040\AppData\Roaming\Typora\typora-user-images\image-20240425194912882.png)

下断点至line70

free掉了一开始的两个大chunk中的较小的chunkp2

在这时我们有一个chunk在largebin（chunkp1）中 另一个在unsortedbin中（chunkp2）

![image-20240425195537640](C:\Users\16040\AppData\Roaming\Typora\typora-user-images\image-20240425195537640.png)

下断点至line75

执行了

```python
p1[3] = (size_t)((&target)-4);
```

将target的地址-0x20写入p1->bk_nextsize

可以看到已经被修改的p1为

![image-20240425200051072](C:\Users\16040\AppData\Roaming\Typora\typora-user-images\image-20240425200051072.png)

下断点至line83

申请了chunkg4 = malloc(0x438)（需要比chunkp2大），与此同时刚刚位于unsorted bin的chunkp2进入了largebin中

如果新插入至largebin的chunk比现在在largebin里的最小的还要小，由于glibc不会检查chunk->bk_nextsize 所以在这里即使修改了p1->bk_nextsize也不会触发任何错误

至此，target变量已经被改写为p2的chunk地址

![image-20240425202726384](C:\Users\16040\AppData\Roaming\Typora\typora-user-images\image-20240425202726384.png)

其实可以用一句代码总结

``` C
fwd->fd->bk_nextsize->fd_nextsize = victim;
```

其中，fwd->fd 就是 largebin chunk，我们只需要修改他的 bk_nextsize 为目标地址 - 0x20，那么就可以对目标地址进行写入，进而达到利用的目的。

我们可以画一个图来展示这个流程

![image-20240425205535880](C:\Users\16040\AppData\Roaming\Typora\typora-user-images\image-20240425205535880.png)

所以我们要做的就是在一开始两个chunk里较大的那个largebin chunk free后修改他的 bk_nextsize 为目标地址 - 0x20即可

效果就是在任意处可以写一个地址