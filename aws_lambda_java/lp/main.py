from linebot import (
    LineBotApi, WebhookHandler
)
from linebot.models import (
     TextSendMessage,
)
import logging
import os

logger = logging.getLogger()
logger.setLevel(logging.INFO)

line_bot_api = LineBotApi(os.getenv('CHANNEL_ACCESS_TOKEN'))
handler = WebhookHandler(os.getenv('CHANNEL_SECRET'))


def lambda_handler(event, context):
    line_bot_api.reply_message(
            event['events'][0]['replyToken'],
            TextSendMessage(text=event['events'][0]['message']['text']))
    return {'statusCode': 200, 'body': 'OK'}
