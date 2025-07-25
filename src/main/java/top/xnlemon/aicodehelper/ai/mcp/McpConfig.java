package top.xnlemon.aicodehelper.ai.mcp;

import dev.langchain4j.mcp.McpToolProvider;
import dev.langchain4j.mcp.client.DefaultMcpClient;
import dev.langchain4j.mcp.client.McpClient;
import dev.langchain4j.mcp.client.transport.McpTransport;
import dev.langchain4j.mcp.client.transport.http.HttpMcpTransport;
import dev.langchain4j.mcp.client.transport.stdio.StdioMcpTransport;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;


@Configuration
public class McpConfig {


    @Value("${bigmodel.api-key}")
    private String apikey;

    @Bean
    public McpToolProvider mcpToolProvider() {
    McpTransport transport = new HttpMcpTransport.Builder()
            .sseUrl("https://open.bigmodel.cn/api/mcp/web_search/sse?Authorization=" + apikey)
            .logRequests(true) // if you want to see the traffic in the log
            .logResponses(true)
            .build();

    McpClient mcpClient = new DefaultMcpClient.Builder()
            .key("MyMCPClient")
            .transport(transport)
            .build();

    McpToolProvider toolProvider = McpToolProvider.builder()
            .mcpClients(mcpClient)
            .build();

    return toolProvider;

    }
}
