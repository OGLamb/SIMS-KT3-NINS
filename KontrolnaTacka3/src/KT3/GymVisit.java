package KT3;

public class GymVisit {
    private String clanId;
    private String clanIme;
    private String trenerId;
    private String trenerIme;
    private String vremePrijave;
    private String vremeOdjave;

    public GymVisit(String clanId, String clanIme, String trenerId, 
                   String trenerIme, String vremePrijave, String vremeOdjave) {
        this.clanId = clanId;
        this.clanIme = clanIme;
        this.trenerId = trenerId;
        this.trenerIme = trenerIme;
        this.vremePrijave = vremePrijave;
        this.vremeOdjave = vremeOdjave;
    }

    public String toString() {
        return clanId + "," + clanIme + "," + trenerId + "," + trenerIme + "," + vremePrijave + "," + vremeOdjave;
    }

    public String getClanId() { return clanId; }
    public String getClanIme() { return clanIme; }
    public String getTrenerId() { return trenerId; }
    public String getTrenerIme() { return trenerIme; }
    public String getVremePrijave() { return vremePrijave; }
    public String getVremeOdjave() { return vremeOdjave; }
}