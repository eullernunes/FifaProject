
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


class Jogador{

    static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");


    private int id;
    private String knownAs;
    private String fullName;
    private byte overall;
    private int value;
    private String bestPosition;
    private String nacionality;
    private byte age;
    private String clubName;
    private Date joinedOn;



    public Jogador(){
        this.id = -1;
        this.knownAs = "";
        this.fullName = "";
        this.overall = 0;
        this.value = 0;
        this.bestPosition = "";
        this.nacionality = "";
        this.age = 0;
        this.clubName = "";
        this.joinedOn = null;
    }

    public Jogador(int id, String knownAs, String fullName, byte overall, int value, String bestPosition, String nacionality, byte age, String clubName,Date joinedOn){
        this.id = id;
        this.knownAs = knownAs;
        this.fullName = fullName;
        this.overall = overall;
        this.value = value;
        this.bestPosition = bestPosition;
        this.nacionality = nacionality;
        this.age = age;
        this.clubName = clubName;
        this.joinedOn = joinedOn;
    }


    //gets

    public int getId(){ return this.id; }
    public String getKnownAs(){ return this.knownAs; }
    public String getFullName(){ return this.fullName; }
    public byte getOverall(){ return this.overall; }
    public int getValue(){ return this.value; }
    public String getBestPosition(){ return this.bestPosition; }
    public String getNacionality(){ return this.nacionality;  }
    public byte getAge(){ return this.age; }
    public String getClubName(){ return this.clubName; }
    public Date getJoinedOn(){  return joinedOn; }

    
    //sets

    public void setId(int id){ this.id = id; }
    public void setKnownAs(String knownAs){ this.knownAs = knownAs; }
    public void setFullName(String fullName){ this.fullName = fullName; }
    public void setOverall(byte overall){ this.overall = overall; }
    public void setValue(int value){ this.value = value; }
    public void setBestPosition(String bestPosition){ this.bestPosition = bestPosition; }
    public void setNacionality(String nacionality){ this.nacionality = nacionality; }
    public void setAge(byte age){ this.age = age; }
    public void setClubName(String clubName){  this.clubName = clubName; }
    public void setJoinedOn(Date joinedOn) throws Exception{ this.joinedOn = joinedOn; }


    //converte um objeto em um array de byte
    public byte[] toByteArray() throws IOException{
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(out);

        dos.writeInt(this.id);      
        dos.writeUTF(this.knownAs);
        dos.writeUTF(this.fullName);
        dos.writeByte(this.overall);
        dos.writeInt(this.value);
        dos.writeUTF(this.bestPosition);
        dos.writeUTF(this.nacionality);
        dos.writeByte(this.age);
        dos.writeUTF(this.clubName); 

        long data = this.joinedOn.getTime();
        dos.writeLong(data);

        return out.toByteArray();
    }

    //converte um array de byte em um objeto
    public void fromByteArray(byte[] out) throws Exception{
        ByteArrayInputStream input = new ByteArrayInputStream(out);
        DataInputStream dis = new DataInputStream(input);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

        setId(dis.readInt());
        setKnownAs(dis.readUTF());
        setFullName(dis.readUTF());
        setOverall(dis.readByte());
        setValue(dis.readInt());
        setBestPosition(dis.readUTF());
        setNacionality(dis.readUTF());
        setAge(dis.readByte());
        setClubName(dis.readUTF());

        long data = dis.readLong();
        Date data1 = new Date(data);        
        setJoinedOn(data1);
    }

    public String toString(){
        return "\nKnowAs: "   + getKnownAs()
        +"\nFullName: "     + getFullName()
        +"\nOverall: "      + getOverall()
        +"\nValue: "        + getValue()
        +"\nBestPosition: " + getBestPosition()
        +"\nNacionality: "  + getNacionality()
        +"\nAge: "          + getAge()
        +"\nClubName: "     + getClubName()
        +"\nJoinedOn: "     + sdf.format(getJoinedOn());
        
    }

    public Jogador clone(){
        Jogador cloned = new Jogador();

        cloned.id = this.id;
        cloned.knownAs = this.knownAs;
        cloned.fullName = this.fullName;
        cloned.overall = this.overall;
        cloned.value = this.value;
        cloned.bestPosition =  this.bestPosition;
        cloned.nacionality = this.nacionality;
        cloned.age = this.age;
        cloned.clubName = this.clubName;
        cloned.joinedOn = this.joinedOn ;

        return cloned;

    }
}