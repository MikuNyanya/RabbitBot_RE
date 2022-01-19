package cn.mikulink.rabbitbot.command.everywhere;


import cn.mikulink.rabbitbot.constant.ConstantQRCode;
import cn.mikulink.rabbitbot.entity.CommandProperties;
import cn.mikulink.rabbitbot.sys.annotate.Command;
import cn.mikulink.rabbitbot.qrcodes.QRCodeUtil;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.User;
import net.mamoe.mirai.internal.message.OnlineImage;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.PlainText;
import net.mamoe.mirai.utils.ExternalResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.util.ArrayList;


/**
 * @author MikuLink
 * @date 2021/12/6 16:23
 * for the Reisen
 * <p>
 * 渐变色二维码
 */
@Command
public class QRCodeGradientCommand extends BaseEveryWhereCommand {
    private static final Logger logger = LoggerFactory.getLogger(QRCodeGradientCommand.class);

    @Override
    public CommandProperties properties() {
        return new CommandProperties("QRCodeGradient", "渐变二维码");
    }

    @Override
    public Message execute(User sender, ArrayList<String> args, MessageChain messageChain, Contact subject) {
        try {
            if (null == args || args.size() == 0) {
                return new PlainText(ConstantQRCode.GRADIENT_EXPLAIN);
            }

            //解析参数 如果只有一个参数，则作为内容
            //入参如果为三个，则前两个解析为颜色参数
            //如果前两个不是颜色参数，则所有参数拼接起来作为二维码内容
            Color onColorStart = null;
            Color onColorEnd = null;
            String logoUrl = null;
            StringBuilder qrContext = new StringBuilder();
            //标识指令类型 1.常规二维码 2.带颜色的二维码 3.带logo的二维码 4.带颜色和logo的二维码
            int type = 2;
            //判断是否带有颜色
            if (args.size() < 3) {
                return new PlainText(ConstantQRCode.GRADIENT_QRCODE_COLOR_ERROR);
            }

            //前两个参数是否为颜色
            if (!args.get(0).startsWith("#") || !args.get(1).startsWith("#")) {
                return new PlainText(ConstantQRCode.QRCODE_COLOR_ERROR);
            }

            try {
                onColorStart = new Color(Integer.parseInt(args.get(0).substring(1), 16));
                onColorEnd = new Color(Integer.parseInt(args.get(1).substring(1), 16));
            } catch (Exception ex) {
                logger.warn("二维码颜色参数转化错误", ex);
                return new PlainText(ConstantQRCode.QRCODE_COLOR_ERROR);
            }

            //判断是否带有logo
            if (args.size() >= 4 && (args.get(2).equals("[图片]") || args.get(2).equals("\n[图片]\n"))) {
                //第三个参数是logo
                logoUrl = ((OnlineImage) messageChain.get(2)).getOriginUrl();
                type = 4;
            }

            for (int i = 0; i < args.size(); i++) {
                //仅带有颜色的请求，忽略前两个参数
                if (type == 2 && i <= 1) {
                    continue;
                }
                //带有logo和图片的请求，忽略前三个参数
                if (type == 4 && i <= 2) {
                    continue;
                }

                qrContext.append(args.get(i));
                qrContext.append(" ");
            }

            if (qrContext.length() > ConstantQRCode.QRCODE_CONTEXT_MAX_LENGTH) {
                return new PlainText(ConstantQRCode.QRCODE_FAIL_OVER_LEN);
            }

            byte[] qrBytes = QRCodeUtil.createGradientColorQRCodeByte(qrContext.toString(), onColorStart, onColorEnd, logoUrl);
            return subject.uploadImage(ExternalResource.create(qrBytes));
        } catch (Exception ex) {
            logger.error("渐变二维码转化失败", ex);
            return new PlainText(ConstantQRCode.QRCODE_FAIL);
        }
    }
}
