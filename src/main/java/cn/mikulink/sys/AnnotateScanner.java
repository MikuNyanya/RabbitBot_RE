package cn.mikulink.sys;

import cn.mikulink.command.Command;
import lombok.Getter;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author MikuLink
 * @date 2020/12/15 16:10
 * for the Reisen
 * <p>
 * 扫描自定义注解
 * <p>
 * 也可以使用反射框架Reflections
 */
@Component
public class AnnotateScanner implements ApplicationListener<ContextRefreshedEvent> {
    @Getter
    List<Command> commandList = new ArrayList<>();

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // 根容器为Spring容器
        //自动扫描注解为@Command的
        if (event.getApplicationContext().getParent() == null) {
            Map<String, Object> beans = event.getApplicationContext().getBeansWithAnnotation(cn.mikulink.sys.annotate.Command.class);
            for (Object bean : beans.values()) {
                if(bean instanceof Command){
                    commandList.add((Command)bean);
                }
            }
        }
    }
}
