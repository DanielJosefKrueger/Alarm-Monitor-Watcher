package configuration;

import org.aeonbits.owner.Config;


@Config.HotReload
@Config.Sources("file:${emailconfig}")
public interface Configuration extends Config {

    @Key("username")
    String username();

    @Key("password")
    String password();

    @Key("SMTP_Host")
    String smtpHost();

    @Key("SMTP_Port")
    String smtpPort();

    @Key("Start_Tls")
    String startTls();

    @Key("SMTP_Auth")
    String smtpAuth();

    @DefaultValue("Alarmmonitor kritischer Fehler")
    @Key("email_betreff")
    String getEmailTopic();

    @Key("emailadressen")
    String getEmailReceiver();

    @DefaultValue("30")
    @Key("interval_minutes")
    int getIntervalMinutes();

    @DefaultValue("11337")
    @Key("port")
    int getPort();



}
