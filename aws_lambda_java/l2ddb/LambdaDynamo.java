package name.qd.lambda_dyno;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class LambdaDynamo implements RequestHandler<Person, String> {
	private String TABLE_NAME = "Person";
	
	@Override
	public String handleRequest(Person person, Context context) {
		LambdaLogger logger = context.getLogger();
		AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder.defaultClient();
		String uuid = UUID.randomUUID().toString();
		
		Map<String, AttributeValue> item = new HashMap<>();
		item.put("id", new AttributeValue(uuid));
		item.put("name", new AttributeValue(person.getName()));
		item.put("age", new AttributeValue(String.valueOf(person.getAge())));
		
		
		PutItemResult putItemResult = amazonDynamoDB.putItem(TABLE_NAME, item);
		logger.log("UUID:" + uuid);
		logger.log("Insert db result:" + putItemResult.toString());
		//
		Map<String, AttributeValue> keyItem = new HashMap<>();
		keyItem.put("id", new AttributeValue(uuid));
		Map<String, AttributeValue> result = amazonDynamoDB.getItem(TABLE_NAME, keyItem).getItem();
		
		for(String key : result.keySet()) {
			logger.log(key + ":" + result.get(key).toString());
		}
		
		//
		keyItem.clear();
		keyItem.put("name", new AttributeValue(person.getName()));
		result = amazonDynamoDB.getItem(TABLE_NAME, keyItem).getItem();
		
		for(String key : result.keySet()) {
			logger.log(key + ":" + result.get(key).toString());
		}
		
		//
		keyItem.clear();
		keyItem.put("age", new AttributeValue(String.valueOf(person.getAge())));
		
		result = amazonDynamoDB.getItem(TABLE_NAME, keyItem).getItem();
		for(String key : result.keySet()) {
			logger.log(key + ":" + result.get(key).toString());
		}
		
		return "";
	}
}
