// Alex Nguyen
// atn170001

public class Player{

    // Player stats
    private int hit;
    private int out;
    private int strikeout;
    private int walk;
    private int hitByPitch;
    private int sacrifice;
    private String name;

    /**
     * Default constructor of player
     */
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

    /**
     * Constructor for specific player
     * @param h number of hits
     * @param o number of outs
     * @param k nmber of strikeouts
     * @param w number of walks
     * @param p number of hit by pitches
     * @param s number of sacrifices
     * @param n name of player
     */
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
    
    /**
     * Returns number of hits
     * @return number of hits
     */
    public int getHits()
    {
    	return hit;
    }
    
    /**
     * Returns number of outs
     * @return number of outs
     */
    public int getOuts()
    {
    	return out;
    }
    
    /**
     * Returns number of strikeouts
     * @return number of strikeouts
     */
    public int getStrikeouts()
    {
    	return strikeout;
    }
    
    /**
     * Returns number of walks
     * @return number of walks
     */
    public int getWalks()
    {
    	return walk;
    }
    
    /**
     * Returns number of hit by pitches
     * @return number of hit by pitches
     */
    public int getHitByPitches()
    {
    	return hitByPitch;
    }
    
    /**
     * Returns number of sacrifices
     * @return number of sacrifices
     */
    public int getSacrifices()
    {
    	return sacrifice;
    }
    
    /**
     * Returns name of player
     * @return name of player
     */
    public String getName()
    {
    	return name;
    }
    
    /**
     * Sets the number of hits
     * @param h number of hits
     */
    public void setHits(int h)
    {
    	hit = h;
    }
    
    /**
     * Sets the number of outs
     * @param o number of outs
     */
    public void setOuts(int o)
    {
    	out = o;
    }
    
    /**
     * Sets the number of strikeouts
     * @param k number of strikeouts
     */
    public void setStrikeOuts(int k)
    {
    	strikeout = k;
    }
    
    /**
     * Sets the number of walks
     * @param w number of walks
     */
    public void setWalks(int w)
    {
    	walk = w;
    }
    
    /**
     * Sets the number of hit by pitches
     * @param p number of hit by pitches
     */
    public void setHitByPitches(int p)
    {
    	hitByPitch = p;
    }
    
    /**
     * Sets the number of sacrifices
     * @param s number of sacrifices
     */
    public void setSacrifices(int s)
    {
    	sacrifice = s;
    }

    public void setName(String name)
    {
        this.name = name;
    }
    
    /**
     * Calculates the number of at bats
     * @param hits number of hits 
     * @param out number of outs
     * @param strikeouts number of strikeouts
     * @return the number of at bats of player
     */
    public int calculateNumAtBats(int hits, int out, int strikeouts)
    {
        return hits + out + strikeouts;
    }
    
    /**
     * Calculates the batting average
     * @param h number of hits
     * @param numAtBats number of bats
     * @return the batting average of player
     */
    public double calculateBattingAverage(int h, int numAtBats)
    {
        if(numAtBats == 0)
            return 0.0;
        else
            return (double)h / numAtBats;
    }
    
    /**
     * Calcultes the number of plate appearances
     * @param hits number of hits
     * @param out number of outs
     * @param strikeouts number of strikeouts
     * @param walks number of walks
     * @param hitByPitch number of hit by pitches
     * @param sacrifice number of sacrifices
     * @return the number of plate appearances of player
     */
    public int calculatePlateAppearances(int hits, int out, int strikeouts, int walks, int hitByPitch, int sacrifice)
    {
        return hits + out + strikeouts + walks + hitByPitch + sacrifice;
    }
    
    /**
     * Calculates the on base percentage
     * @param h number of hits
     * @param w number of walks
     * @param p number of hit by pitches
     * @param plateAppearances number of plate appearances     
     * @return the on base percentage of player
     */
    public double calculateOnBasePercentage(int h, int w, int p, int plateAppearances)
    {
        if(plateAppearances == 0)
            return 0.0;
        else
            return ((double)(h + w + p)) / plateAppearances;
    }
    
}
