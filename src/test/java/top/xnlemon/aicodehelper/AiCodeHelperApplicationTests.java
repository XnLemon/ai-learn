package top.xnlemon.aicodehelper;

import dev.langchain4j.data.message.ImageContent;
import dev.langchain4j.data.message.TextContent;
import dev.langchain4j.data.message.UserMessage;
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

    @Test
    //need model supported Modalities
    void chatwithmessage() {
        UserMessage userMessage = UserMessage.from(
                TextContent.from("请为Nene7ko描述此张图片"),
                ImageContent.from("https://s2.loli.net/2025/07/10/VkCyflJNEvdKU3n.jpg")
        );
        aiCodeHelper.chatwithmessage(userMessage);
    }
}
