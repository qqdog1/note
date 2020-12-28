package name.qd.lambdaTest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.message.TextMessage;

public class LBotEntry implements RequestStreamHandler {
	private LambdaLogger logger;
	private LineMessagingClient lineMessagingClient;

	@Override
	public void handleRequest(InputStream input, OutputStream output, Context context) {
		logger = context.getLogger();

		try {
			lineMessagingClient = LineMessagingClient.builder(System.getenv("CHANNEL_ACCESS_TOKEN")).build();
			
			String inputMessage = IOUtils.toString(input, "UTF-8");

			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			objectMapper.registerModule(new JavaTimeModule())
					.configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
			JsonNode node = objectMapper.readTree(inputMessage);
			
			String replyToken = node.get("events").get(0).get("replyToken").asText();
			String text = node.get("events").get(0).get("message").get("text").asText() + " --> lambda";
			
			lineMessagingClient.replyMessage(new ReplyMessage(replyToken, new TextMessage(text)));
			
			output.write("OK".getBytes());
		} catch (IOException e) {
			logger.log(e.getMessage());
		}
	}
}
