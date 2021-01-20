import json
import boto3

dynamodb = boto3.client('dynamodb')


def lambda_handler(event, context):
    put_result = dynamodb.put_item(TableName='Person', Item={'id':{'S':'1a2b3c'},'name':{'S':'Banana'},'age':{'N':'1'}})
    get_result = dynamodb.get_item(TableName='Person', Key={'id':{'S':'b1223936-76c6-4c23-b29a-19e119653697'}})
    # TODO implement
    return {
        'statusCode': 200,
        'body': json.dumps(put_result)
    }
