
import javax.net.SocketFactory;
import org.pircbotx.PircBotX;
import org.pircbotx.UtilSSLSocketFactory;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.ConnectEvent;
import org.pircbotx.hooks.events.DisconnectEvent;
import org.pircbotx.hooks.events.JoinEvent;
import org.pircbotx.hooks.events.KickEvent;
import org.pircbotx.hooks.events.MessageEvent;

public class mod_irc extends BaseMod
{
    public static mod_irc instance;
    private PircBotX connection = new PircBotX();
    private String host = "irc.esper.net";
    private int port = 6697;
    private boolean ssl = true;
    private String password;
    private String username = "minecraft";
    private String activeChan = "#md_5";
    private String prefix = "@ ";
    private String commandPrefix = "-";

    public mod_irc()
    {
        instance = this;
    }

    public void load()
    {
        Runtime.getRuntime().addShutdownHook(new Thread()
        {
            @Override
            public void run()
            {
                connection.quitServer();
            }
        });
        new Thread()
        {
            @Override
            public void run()
            {
                connection.setVerbose(true);
                connection.setLogin(username);
                connection.setName(ModLoader.getMinecraftInstance().k.b + "`mc");
                try
                {
                    SocketFactory socket = ssl ? new UtilSSLSocketFactory().disableDiffieHellman().trustAllCertificates() : null;
                    if (password == null)
                    {
                        connection.connect(host, port, socket);
                    }
                    else
                    {
                        connection.connect(host, port, password, socket);
                    }
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
                connection.getListenerManager().addListener(new Listener());
                connection.joinChannel(activeChan);
                System.out.println("Connected to " + host + ":" + port + " as " + connection.getNick() + " in channel " + activeChan);
            }
        }.start();
    }

    public boolean handleChat(String message)
    {
        if (message.startsWith(this.prefix))
        {
            sendMessage(message.replaceFirst(this.prefix, ""));
            return true;
        }
        if (message.startsWith(this.commandPrefix))
        {
            String[] command = message.replaceFirst(this.commandPrefix, "").split(" ");
            if (command[0].startsWith("join"))
            {
                if (command.length == 2)
                {
                    this.connection.partChannel(this.connection.getChannel(this.activeChan));
                    addToChat("Left " + this.activeChan);
                    this.activeChan = command[1];
                    this.connection.joinChannel(this.activeChan);
                }
                else
                {
                    addToChat(" Please review your argument count");
                }
            }
            return true;
        }

        return false;
    }

    public void sendMessage(String message)
    {
        sendMessage(this.activeChan, message);
    }

    public void sendMessage(String target, String message)
    {
        this.connection.sendMessage(target, message);
        addToChat("<" + this.connection.getNick() + "> " + message);
    }

    private void addToChat(String message)
    {
        if (ModLoader.getMinecraftInstance().l())
        {
            ModLoader.getMinecraftInstance().w.a("[IRC] " + message);
        }
    }

    public String getVersion()
    {
        return "1.0";
    }

    private class Listener extends ListenerAdapter
    {
        @Override
        public void onConnect(ConnectEvent event) throws Exception
        {
            addToChat("Connected to " + connection.getServer());
        }

        @Override
        public void onDisconnect(DisconnectEvent event) throws Exception
        {
            addToChat("Disconnected from " + connection.getServer());
        }

        @Override
        public void onMessage(MessageEvent event) throws Exception
        {
            addToChat("<" + event.getUser().getNick() + "> " + event.getMessage());
        }

        @Override
        public void onJoin(JoinEvent event) throws Exception
        {
            addToChat("Joined " + event.getChannel().getName());
        }

        @Override
        public void onKick(KickEvent event) throws Exception
        {
            addToChat("Kicked from " + event.getChannel() + " (" + event.getReason() + ")");
        }
    }
}
