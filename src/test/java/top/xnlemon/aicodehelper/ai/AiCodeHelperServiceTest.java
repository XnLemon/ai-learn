package top.xnlemon.aicodehelper.ai;

import dev.langchain4j.service.Result;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class AiCodeHelperServiceTest {

    @Resource
    AiCodeHelperService aiCodeHelperService;

    @Test
    public void chat() {
        String Result  = aiCodeHelperService.chat("你好我是XnLemon");
        System.out.println(Result);
    }

    @Test
    //这里是在内存内 实际开发应考虑MySQL 实现ChatMemoryStore接口
    public void chatwithMemory() {
        String Result  = aiCodeHelperService.chat("你好我是XnLemon");
        System.out.println(Result);
        String result = aiCodeHelperService.chat("我是谁?");
        System.out.println(result);
    }

    @Test
    public void chatwithRag() {
        Result<String> Result  = aiCodeHelperService.chatwithRag("你好我是XnLemon,请你为我列举出Heap常用的攻击方式");
        System.out.println(Result.sources());
        System.out.println(Result.content());

        Result<String> result = aiCodeHelperService.chatwithRag("我是谁?");
        System.out.println(result);
    }

}