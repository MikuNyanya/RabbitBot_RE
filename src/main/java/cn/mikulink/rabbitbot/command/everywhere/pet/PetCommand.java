package cn.mikulink.rabbitbot.command.everywhere.pet;

import cn.mikulink.rabbitbot.command.everywhere.BaseEveryWhereCommand;
import cn.mikulink.rabbitbot.constant.ConstantPet;
import cn.mikulink.rabbitbot.entity.CommandProperties;
import cn.mikulink.rabbitbot.service.PetService;
import cn.mikulink.rabbitbot.sys.annotate.Command;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.User;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.PlainText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;
import java.util.ArrayList;


/**
 * @author MikuLink
 * @date 2021/09/16 17:50
 * for the Reisen
 * <p>
 * 养成状态
 */
@Command
public class PetCommand extends BaseEveryWhereCommand {

    //兔叽
    @Value("${bot.name.cn:兔叽}")
    public String rabbit_bot_name;
    //RabbitBot
    @Value("${bot.name.en:RabbitBot}")
    public String rabbit_bot_name_en;

    @Autowired
    private PetService petService;

    @Override
    public CommandProperties properties() {
        return new CommandProperties("Pet", "兔叽", "养成", "养成系统");
    }

    @Override
    public Message execute(User sender, ArrayList<String> args, MessageChain messageChain, Contact subject) {
        StringBuilder msg = new StringBuilder();
        msg.append(rabbit_bot_name).append("(").append(rabbit_bot_name_en).append(")\n");
        msg.append("等级 ").append(petService.getExpBar()).append(" lv").append(ConstantPet.petInfo.getLevel()).append("\n");
        msg.append("心情 ").append(petService.getHeartBar()).append(" ").append(ConstantPet.petInfo.getHeart()).append("\n");
        msg.append("======================");

        return new PlainText(msg);
    }
}
