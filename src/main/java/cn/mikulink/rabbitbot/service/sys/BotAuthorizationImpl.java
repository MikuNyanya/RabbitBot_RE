package cn.mikulink.rabbitbot.service.sys;

import kotlin.coroutines.Continuation;
import net.mamoe.mirai.auth.BotAuthInfo;
import net.mamoe.mirai.auth.BotAuthResult;
import net.mamoe.mirai.auth.BotAuthSession;
import net.mamoe.mirai.auth.BotAuthorization;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BotAuthorizationImpl implements BotAuthorization {
    private final String botPwd;
    private final boolean isQR;

    public BotAuthorizationImpl(String pwd, boolean isQR) {
        botPwd = pwd;
        this.isQR = isQR;
    }

    @Nullable
    @Override
    public Object authorize(@NotNull BotAuthSession botAuthSession, @NotNull BotAuthInfo botAuthInfo, @NotNull Continuation<? super BotAuthResult> continuation) {
        try {
            if (isQR) {
                return botAuthSession.authByQRCode(continuation);
            }
            else {
                return botAuthSession.authByPassword(botPwd, continuation);
            }
        } catch (Exception e) {
            return botAuthSession.authByPassword(botPwd, continuation);
        }
    }
}
