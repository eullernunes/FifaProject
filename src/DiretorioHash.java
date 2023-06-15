import java.io.RandomAccessFile;

class DiretorioHash
{
    private RandomAccessFile raf = null;
    private final String nome = "diretorioHash.index";
    private short profundidade;

    public DiretorioHash() 
    {
        try 
        {
            raf = new RandomAccessFile(nome, "rw");
            if(raf.length()==0)
            {
                profundidade = 0;
                raf.writeShort(0);
                raf.writeLong(0);
            } else 
            {
                raf.seek(0);
                profundidade = raf.readShort();
            }
            raf.close();
        } catch (Exception e) 
        {
            System.out.println("Erro ao abrir o arquivo de diretório!");
        }
    }

    public String toString()
    {
        String resp = "";
        resp += "p = " + profundidade;
        try 
        {
            raf = new RandomAccessFile(nome, "r");
            for(int i=0, n=(int)Math.pow(2,profundidade); i<n; i++)
            {
                raf.seek(hash(i));
                resp += "\n" + i + ": " + raf.readLong();
            }
            raf.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
        return resp;
    }

    public void updateBucket(int id, long novoEndereco, int profundidadeBucket)
    {
        long hash = hash(id);
        System.out.println("hash = "+hash);
        int total = (int)Math.pow(2,profundidade);
        try 
        {
            raf = new RandomAccessFile(nome, "rw");
            raf.seek(hash);
            raf.writeLong(novoEndereco);
            int prox = (int)Math.pow(2,profundidadeBucket);
            for(int i=id+prox; i<total; i+=prox)
            {
                hash = hash(i);
                raf.writeLong(novoEndereco);
            }
            raf.close();
        } catch (Exception e)
        {
            System.out.println("Erro ao abrir arquivo de diretório para atualizar endereço de bucket!");
        }
    }

    public short getProfundidade()
    {
        return profundidade;
    }

    public void aumentaProfundidade()
    {
        profundidade++;
        try 
        {
            raf = new RandomAccessFile(nome, "rw");
            raf.seek(0);
            raf.writeShort(profundidade);
            raf.close();

            raf = new RandomAccessFile(nome, "r");
            raf.seek(2);
            RandomAccessFile raf2 = new RandomAccessFile(nome, "rw");

            raf2.seek(raf2.length());

            int total = (int)Math.pow(2,profundidade-1);
            System.out.println(total);
            for(int i=0; i<total; i++)
            {
                System.out.println("raf1 = "+raf.getFilePointer());
                System.out.println("raf2 = "+raf2.getFilePointer());
                raf2.writeLong(raf.readLong());
            }
            
            raf2.close();
            raf.close();
        } catch (Exception e) 
        {
            System.out.println("Erro ao abrir o arquivo de diretório para aumentar a profundidade!");
        }
    }

    private long hash(int id)
    {
        return (id % (int)Math.pow(2,profundidade))*8 + 2;
    }

    public long endereco(int id)
    {
        long endResto = hash(id);
        long resp = -1;
        try 
        {
            raf = new RandomAccessFile(nome, "rw");
            raf.seek(endResto);
            resp = raf.readLong();
            raf.close();
        } catch (Exception e) 
        {
            System.out.println("Erro ao abrir o arquivo de diretório para achar id!");
        }
        return resp;
    }
}