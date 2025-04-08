package cn.mikulink.rabbitbot.command.everywhere;


import cn.mikulink.rabbitbot.command.EverywhereCommand;
import cn.mikulink.rabbitbot.entity.CommandProperties;
import cn.mikulink.rabbitbot.entity.rabbitbotmessage.MessageInfo;
import cn.mikulink.rabbitbot.sys.annotate.Command;
import lombok.extern.slf4j.Slf4j;


/**
 * @author MikuLink
 * @date 2021/12/6 16:23
 * for the Reisen
 * <p>
 * 二维码指令
 */
@Slf4j
@Command
public class QRCodeCommand extends EverywhereCommand {
    @Override
    public CommandProperties properties() {
        return new CommandProperties("QRCode", "QR","二维码");
    }

    @Override
    public MessageInfo execute(MessageInfo messageInfo) {
        //todo 整理
//        List<String> args = getArgs(messageInfo.getRawMessage());
//        try {
//
//            if (null == args || args.size() == 0) {
//                return RabbitBotMessageBuilder.createMessageText(ConstantQRCode.EXPLAIN);
//            }
//
//            //解析参数 如果只有一个参数，则作为内容
//            //入参如果为三个，则前两个解析为颜色参数
//            //如果前两个不是颜色参数，则所有参数拼接起来作为二维码内容
//            Color onColor = null;
//            Color offColor = null;
//            String logoUrl = null;
//            StringBuilder qrContext = new StringBuilder();
//            //标识指令类型 1.常规二维码 2.带颜色的二维码 3.带logo的二维码 4.带颜色和logo的二维码
//            int type = 1;
//            //判断是否带有颜色
//            if (args.size() >= 3) {
//                //前两个参数是否为颜色
//                if (args.get(0).startsWith("#") && args.get(1).startsWith("#")) {
//                    type = 2;
//                    try {
//                        onColor = new Color(Integer.parseInt(args.get(0).substring(1), 16));
//                        offColor = new Color(Integer.parseInt(args.get(1).substring(1), 16));
//                    } catch (Exception ex) {
//                        logger.warn("二维码颜色参数转化错误", ex);
//                        return RabbitBotMessageBuilder.createMessageText((ConstantQRCode.QRCODE_COLOR_ERROR);
//                    }
//                }
//            }
//
//            //判断是否带有logo
//            if (args.get(0).equals("[图片]") || args.get(0).equals("\n[图片]\n")) {
//                //第一个参数是logo
//                logoUrl = ((OnlineGroupImageImpl) messageChain.get(2)).getOriginUrl();
//                type = 3;
//            }
//            if (args.size() >= 4 && (args.get(2).equals("[图片]") || args.get(2).equals("\n[图片]\n"))) {
//                //第三个参数是logo
//                logoUrl = ((OnlineGroupImageImpl) messageChain.get(2)).getOriginUrl();
//                type = 4;
//            }
//
//            for (int i = 0; i < args.size(); i++) {
//                //仅带有logo的请求，忽略第一个参数
//                if (type == 3 && i == 0) {
//                    continue;
//                }
//                //仅带有颜色的请求，忽略前两个参数
//                if (type == 2 && i <= 1) {
//                    continue;
//                }
//                //带有logo和图片的请求，忽略前三个参数
//                if (type == 4 && i <= 2) {
//                    continue;
//                }
//
//                qrContext.append(args.get(i));
//                qrContext.append(" ");
//            }
//
//            if (qrContext.length() > ConstantQRCode.QRCODE_CONTEXT_MAX_LENGTH) {
//                return RabbitBotMessageBuilder.createMessageText(ConstantQRCode.QRCODE_FAIL_OVER_LEN);
//            }
//
//            byte[] qrBytes = QRCodeUtil.createQRCodeByte(qrContext.toString(), onColor, offColor, logoUrl);
//            return subject.uploadImage(ExternalResource.create(qrBytes).toAutoCloseable());
//        } catch (Exception ex) {
//            log.error("二维码转化失败", ex);
//            return RabbitBotMessageBuilder.createMessageText(ConstantQRCode.QRCODE_FAIL);
//        }
        return null;
    }
}
