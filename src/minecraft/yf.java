
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class yf extends vp
{
    private String b;
    private int c;
    private boolean d;
    private String e;
    private String f;
    private int h;
    private List i;
    private URI j;
    protected agu a;
    private String k;

    public yf()
    {
        this.b = "";
        this.c = -1;
        this.d = false;
        this.e = "";
        this.f = "";
        this.h = 0;
        this.i = new ArrayList();
        this.j = null;
        this.k = "";
    }

    public yf(String par1Str)
    {
        this.b = "";
        this.c = -1;
        this.d = false;
        this.e = "";
        this.f = "";
        this.h = 0;
        this.i = new ArrayList();
        this.j = null;
        this.k = "";
        this.k = par1Str;
    }

    public void c()
    {
        Keyboard.enableRepeatEvents(true);
        this.c = this.p.w.c().size();
        this.a = new agu(this.u, 4, this.r - 12, this.q - 4, 12);
        this.a.f(100);
        this.a.a(false);
        this.a.b(true);
        this.a.a(this.k);
        this.a.c(false);
    }

    public void e()
    {
        Keyboard.enableRepeatEvents(false);
        this.p.w.d();
    }

    public void a()
    {
        this.a.a();
    }

    protected void a(char par1, int par2)
    {
        if (par2 == 15)
        {
            d();
        }
        else
        {
            this.d = false;
        }

        if (par2 == 1)
        {
            this.p.g();
        }
        else if (par2 == 28)
        {
            String s = this.a.b().trim();

            if ((s.length() > 0) && (!this.p.c(s)) && (!mod_irc.instance.handleChat(s)))
            {
                this.p.h.a(s);
            }

            this.p.g();
        }
        else if (par2 == 200)
        {
            a(-1);
        }
        else if (par2 == 208)
        {
            a(1);
        }
        else if (par2 == 201)
        {
            this.p.w.a(19);
        }
        else if (par2 == 209)
        {
            this.p.w.a(-19);
        }
        else
        {
            this.a.a(par1, par2);
        }
    }

    public void f()
    {
        super.f();
        int i = Mouse.getEventDWheel();

        if (i != 0)
        {
            if (i > 1)
            {
                i = 1;
            }

            if (i < -1)
            {
                i = -1;
            }

            if (!m())
            {
                i *= 7;
            }

            this.p.w.a(i);
        }
    }

    protected void a(int par1, int par2, int par3)
    {
        if (par3 == 0)
        {
            dx chatclickdata = this.p.w.a(Mouse.getX(), Mouse.getY());

            if (chatclickdata != null)
            {
                URI uri = chatclickdata.b();

                if (uri != null)
                {
                    this.j = uri;
                    this.p.a(new be(this, this, chatclickdata.a(), 0, chatclickdata));
                    return;
                }
            }
        }

        this.a.a(par1, par2, par3);
        super.a(par1, par2, par3);
    }

    public void a(boolean par1, int par2)
    {
        if (par2 == 0)
        {
            if (par1)
            {
                try
                {
                    Class class1 = Class.forName("java.awt.Desktop");
                    Object obj = class1.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
                    class1.getMethod("browse", new Class[]
                            {
                                URI.class
                            }).invoke(obj, new Object[]
                            {
                                this.j
                            });
                }
                catch (Throwable throwable)
                {
                    throwable.printStackTrace();
                }
            }

            this.j = null;
            this.p.a(this);
        }
    }

    public void d()
    {
        if (this.d)
        {
            this.a.a(-1);

            if (this.h >= this.i.size())
            {
                this.h = 0;
            }
        }
        else
        {
            int i = this.a.c(-1);

            if (this.a.h() - i < 1)
            {
                return;
            }

            this.i.clear();
            this.e = this.a.b().substring(i);
            this.f = this.e.toLowerCase();
            Iterator iterator = ((ahv)this.p.h).cl.c.iterator();

            while (iterator.hasNext())
            {
                ah guiplayerinfo = (ah)iterator.next();

                if (guiplayerinfo.a(this.f))
                {
                    this.i.add(guiplayerinfo);
                }

            }

            if (this.i.size() == 0)
            {
                return;
            }

            this.d = true;
            this.h = 0;
            this.a.b(i - this.a.h());
        }

        if (this.i.size() > 1)
        {
            StringBuilder stringbuilder = new StringBuilder();
            ah guiplayerinfo1;
            for (Iterator iterator1 = this.i.iterator(); iterator1.hasNext(); stringbuilder.append(guiplayerinfo1.a))
            {
                guiplayerinfo1 = (ah)iterator1.next();

                if (stringbuilder.length() <= 0)
                {
                    continue;
                }
                stringbuilder.append(", ");
            }

            this.p.w.a(stringbuilder.toString());
        }

        this.a.b(((ah)this.i.get(this.h++)).a);
    }

    public void a(int par1)
    {
        int i = this.c + par1;
        int j = this.p.w.c().size();

        if (i < 0)
        {
            i = 0;
        }

        if (i > j)
        {
            i = j;
        }

        if (i == this.c)
        {
            return;
        }

        if (i == j)
        {
            this.c = j;
            this.a.a(this.b);
            return;
        }

        if (this.c == j)
        {
            this.b = this.a.b();
        }

        this.a.a((String)this.p.w.c().get(i));
        this.c = i;
    }

    public void a(int par1, int par2, float par3)
    {
        a(2, this.r - 14, this.q - 2, this.r - 2, -2147483648);
        this.a.f();
        super.a(par1, par2, par3);
    }
}
