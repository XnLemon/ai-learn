package top.xnlemon.aicodehelper;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import top.xnlemon.aicodehelper.ai.AiCodeHelper;

@SpringBootTest
class AiCodeHelperApplicationTests {


    @Resource
    private AiCodeHelper aiCodeHelper;

    @Test
    void chat() {
        aiCodeHelper.chat("你好我是Nene7ko");
    }
}
