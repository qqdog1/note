from linebot import (
    LineBotApi, WebhookHandler
)
from linebot.models import (
    TextSendMessage,
)
import boto3
import logging
import os

logger = logging.getLogger()
logger.setLevel(logging.INFO)

line_bot_api = LineBotApi(os.getenv('CHANNEL_ACCESS_TOKEN'))
handler = WebhookHandler(os.getenv('CHANNEL_SECRET'))
client = boto3.client('lambda')


def lambda_handler(event, context):
    response = client.invoke(
        FunctionName='arn:aws:lambda:ap-northeast-1:421225082410:function:simple_ack',
        InvocationType='RequestResponse',
        Payload=''
    )

    response_string = response['Payload'].read().decode("utf-8")

    line_bot_api.reply_message(
        event['events'][0]['replyToken'],
        TextSendMessage(text=event['events'][0]['message']['text'] + response_string))
    return {'statusCode': 200, 'body': 'OK'}
