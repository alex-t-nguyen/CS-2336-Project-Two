public class Player {

    private int hit;
    private int out;
    private int strikeout;
    private int walk;
    private int hitByPitch;
    private int sacrifice;
    private String name;

    public Player()
    {
        hit = 0;
        out = 0;
        strikeout = 0;
        walk = 0;
        hitByPitch = 0;
        sacrifice = 0;
        name = null;
    }

    public Player(int h, int o, int k, int w, int p, int s, String n)
    {
        hit = h;
        out = o;
        strikeout = k;
        walk = w;
        hitByPitch = p;
        sacrifice = s;
        name = n;
    }

    
}