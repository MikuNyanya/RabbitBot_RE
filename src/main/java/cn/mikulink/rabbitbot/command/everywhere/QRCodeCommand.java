package cn.mikulink.rabbitbot.command.everywhere;


import cn.mikulink.rabbitbot.constant.ConstantQRCode;
import cn.mikulink.rabbitbot.entity.CommandProperties;
import cn.mikulink.rabbitbot.sys.annotate.Command;
import cn.mikulink.rabbitbot.utils.QRCodeUtil;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.User;
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
 * 二维码指令
 */
@Command
public class QRCodeCommand extends BaseEveryWhereCommand {
    private static final Logger logger = LoggerFactory.getLogger(QRCodeCommand.class);

    @Override
    public CommandProperties properties() {
        return new CommandProperties("QRCode", "二维码");
    }

    @Override
    public Message execute(User sender, ArrayList<String> args, MessageChain messageChain, Contact subject) {
        try {
            if (null == args || args.size() == 0) {
                return new PlainText(ConstantQRCode.EXPLAIN);
            }

            //解析参数 如果只有一个参数，则作为内容
            //入参如果为三个，则前两个解析为颜色参数
            //如果前两个不是颜色参数，则所有参数拼接起来作为二维码内容
            Color onColor = null;
            Color offColor = null;
            StringBuilder qrContext = new StringBuilder();
            //标识指令类型 1.常规二维码 2.带颜色的二维码
            int type = 1;
            if (args.size() >= 3) {
                //前两个参数是否为颜色
                if (args.get(0).startsWith("#") && args.get(1).startsWith("#")) {
                    type = 2;
                    try {
                        onColor = new Color(Integer.parseInt(args.get(0).substring(1), 16));
                        offColor = new Color(Integer.parseInt(args.get(1).substring(1), 16));
                    } catch (Exception ex) {
                        logger.warn("二维码颜色参数转化错误", ex);
                        return new PlainText(ConstantQRCode.QRCODE_COLOR_ERROR);
                    }
                }
            }

            for (int i = 0; i < args.size(); i++) {
                //如果前两个参数为颜色，则忽略掉
                if (type == 2 && i <= 1) {
                    continue;
                }
                qrContext.append(args.get(i));
                qrContext.append(" ");
            }

            if (qrContext.length() > ConstantQRCode.QRCODE_CONTEXT_MAX_LENGTH) {
                return new PlainText(ConstantQRCode.QRCODE_FAIL_OVER_LEN);
            }

            byte[] qrBytes = QRCodeUtil.createQRCodeByte(qrContext.toString(), onColor, offColor);
            return subject.uploadImage(ExternalResource.create(qrBytes));
        } catch (Exception ex) {
            logger.error("二维码转化失败", ex);
            return new PlainText(ConstantQRCode.QRCODE_FAIL);
        }
    }
}
