package top.xnlemon.aicodehelper.ai;

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
}