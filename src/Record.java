
// -------------------------------------------------------------------------
/**
 *  Record class that contains all the information pertaing to a MLB player.
 *
 *  @author Mack Hasz (mrh17)
 *  @version May 28, 2014
 */
public class Record implements Comparable<Record>, Hashable
{
    private long offset;
    private int lahman_ID;
    private String player_ID;
    private String birthdate;
    private String country_born;
    private String state_born;
    private String city_born;
    private String deathdate;
    private String country_died;
    private String state_died;
    private String city_died;
    private String first_name;
    private String last_name;
    private String weight;
    private String height;
    private String batting_hand;
    private String throwing_hand;
    private String MLB_debut;
    private String MLB_finale;

    public long getOffset()
    {
        return offset;
    }

    public void setOffset(long offset)
    {
        this.offset = offset;
    }

    public int getLahman_ID()
    {
        return lahman_ID;
    }

    public void setLahman_ID(int lahman_ID)
    {
        this.lahman_ID = lahman_ID;
    }

    public String getPlayer_ID()
    {
        return player_ID;
    }

    public void setPlayer_ID(String player_ID)
    {
        this.player_ID = player_ID;
    }

    public String getBirthdate()
    {
        return birthdate;
    }

    public void setBirthdate(String birthdate)
    {
        this.birthdate = birthdate;
    }

    public String getCountry_born()
    {
        return country_born;
    }

    public void setCountry_born(String country_born)
    {
        this.country_born = country_born;
    }

    public String getState_born()
    {
        return state_born;
    }

    public void setState_born(String state_born)
    {
        this.state_born = state_born;
    }

    public String getCity_born()
    {
        return city_born;
    }

    public void setCity_born(String city_born)
    {
        this.city_born = city_born;
    }

    public String getDeathdate()
    {
        return deathdate;
    }

    public void setDeathdate(String deathdate)
    {
        this.deathdate = deathdate;
    }

    public String getCountry_died()
    {
        return country_died;
    }

    public void setCountry_died(String country_died)
    {
        this.country_died = country_died;
    }

    public String getState_died()
    {
        return state_died;
    }

    public void setState_died(String state_died)
    {
        this.state_died = state_died;
    }

    public String getCity_died()
    {
        return city_died;
    }

    public void setCity_died(String city_died)
    {
        this.city_died = city_died;
    }

    public String getFirst_name()
    {
        return first_name;
    }

    public void setFirst_name(String first_name)
    {
        this.first_name = first_name;
    }

    public String getLast_name()
    {
        return last_name;
    }

    public void setLast_name(String last_name)
    {
        this.last_name = last_name;
    }

    public String getWeight()
    {
        return weight;
    }

    public void setWeight(String weight)
    {
        this.weight = weight;
    }

    public String getHeight()
    {
        return height;
    }

    public void setHeight(String height)
    {
        this.height = height;
    }

    public String getBatting_hand()
    {
        return batting_hand;
    }

    public void setBatting_hand(String batting_hand)
    {
        this.batting_hand = batting_hand;
    }

    public String getThrowing_hand()
    {
        return throwing_hand;
    }

    public void setThrowing_hand(String throwing_hand)
    {
        this.throwing_hand = throwing_hand;
    }

    public String getMLB_debut()
    {
        return MLB_debut;
    }

    public void setMLB_debut(String mLB_debut)
    {
        MLB_debut = mLB_debut;
    }

    public String getMLB_finale()
    {
        return MLB_finale;
    }

    public void setMLB_finale(String mLB_finale)
    {
        MLB_finale = mLB_finale;
    }

    // ----------------------------------------------------------
    /**
     * Create a new Record object.
     * @param offset
     * @param lahman_ID
     * @param player_ID
     * @param birthdate
     * @param country_born
     * @param state_born
     * @param city_born
     * @param deathdate
     * @param country_died
     * @param state_died
     * @param city_died
     * @param first_name
     * @param last_name
     * @param weight
     * @param height
     * @param batting_hand
     * @param throwing_hand
     * @param mLB_debut
     * @param mLB_finale
     */
    public Record(
        long offset,
        int lahman_ID,
        String player_ID,
        String birthdate,
        String country_born,
        String state_born,
        String city_born,
        String deathdate,
        String country_died,
        String state_died,
        String city_died,
        String first_name,
        String last_name,
        String weight,
        String height,
        String batting_hand,
        String throwing_hand,
        String mLB_debut,
        String mLB_finale)
    {
        super();
        this.offset = offset;
        this.lahman_ID = lahman_ID;
        this.player_ID = player_ID;
        this.birthdate = birthdate;
        this.country_born = country_born;
        this.state_born = state_born;
        this.city_born = city_born;
        this.deathdate = deathdate;
        this.country_died = country_died;
        this.state_died = state_died;
        this.city_died = city_died;
        this.first_name = first_name;
        this.last_name = last_name;
        this.weight = weight;
        this.height = height;
        this.batting_hand = batting_hand;
        this.throwing_hand = throwing_hand;
        MLB_debut = mLB_debut;
        MLB_finale = mLB_finale;
    }

    public String toString() {
        return lahman_ID + "\t" + player_ID + "\t" + birthdate + "\t" + country_born +
            "\t" + state_born + "\t" + city_born + "\t" +  deathdate + "\t" + country_died
            + "\t" + state_died + "\t" + city_died + "\t" + first_name + "\t" +
            last_name + "\t" + weight + "\t" + height + "\t" + batting_hand + "\t" +
            throwing_hand + "\t" + MLB_debut + "\t" + MLB_finale;
    }

    // hash function is elfHash taken from the course notes
    public int Hash() {
        String toHash = player_ID;
        long hashValue = 0;
        for (int Pos = 0; Pos < toHash.length(); Pos++) {
            hashValue = (hashValue << 4) + toHash.charAt(Pos);
            long hiBits = hashValue & 0xF0000000;
            if(hiBits != 0) {
                hashValue ^= hiBits >> 24;
            }
            hashValue &= ~hiBits;
        }
        return (int)hashValue;
    }

    public int compareTo(Record o)
    {
        return this.player_ID.compareTo(o.player_ID);
    }
}
