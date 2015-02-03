// MiniRecord class that holds the info for AVL tree and hashtable
public class MiniRecord implements Comparable<MiniRecord>, Hashable
{
    private long offset;
    private String player_ID;
    private String first_name;
    private String last_name;

    // ----------------------------------------------------------
    /**
     * Create a new MiniRecord object.
     * @param offset
     * @param player_ID
     * @param first_name
     * @param last_name
     */
    public MiniRecord(
        long offset,
        String player_ID,
        String first_name,
        String last_name)
    {
        super();
        this.offset = offset;
        this.player_ID = player_ID;
        this.first_name = first_name;
        this.last_name = last_name;
    }
    public long getOffset()
    {
        return offset;
    }
    public void setOffset(long offset)
    {
        this.offset = offset;
    }
    public String getPlayer_ID()
    {
        return player_ID;
    }
    public void setPlayer_ID(String player_ID)
    {
        this.player_ID = player_ID;
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

    // hash function is elfHash taken from the course notes
    public int Hash() {
        String toHash = this.last_name;
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

    public String toString() {
        return this.player_ID;
    }

    public boolean equals(Object oh) {
        if (oh == null) {
            return false;
        }

        if (!this.getClass().equals(oh.getClass())) {
            return false;
        }

        MiniRecord other = (MiniRecord) oh;

        return this.last_name.equals(other.last_name);
    }

    public int compareTo(MiniRecord o)
    {
        return this.player_ID.compareTo(o.player_ID);
    }
}
