package kalk2;

import java.util.Vector;

import kalk2.errLabirinto;
import kalk2.errSize;
import kalk2.errSizeProd;
import kalk2.matrice;
import kalk2.matriceBinaria;
import kalk2.matriceBinaria.casella;

class matriceBinaria extends matrice{
	  static public class casella{
	    public int y;
	    public int x;
	    public casella(int r,int c){y=r;x=c;}
	  };
	  private boolean fine_lab(int y,int x,int ye,int xe){
		  if(y==ye && x==xe)
		    return true;
		  return false;
	  }
	  private boolean bloccato(matrice m,int y,int x){
		  int n=0;
		  if(y+1>=m.getRows() || m.read(y+1,x)==1 || m.read(y+1,x)==3)n++;
		  if(y-1<0 || m.read(y-1,x)==1 || m.read(y-1,x)==3 )n++;
		  if(x+1>=m.getColumns() || m.read(y,x+1)==1 || m.read(y,x+1)==3)n++;
		  if(x-1<0 || m.read(y,x-1)==1 || m.read(y,x-1)==3)n++;
		  if(n==4)return true;
		  return false;
		}
	  private boolean is_best_path(Vector<casella> p,int y,int x){
		  for(int i=0;i<p.size();i++)
		    if(y==p.get(i).y && x==p.get(i).x)
		      return true;
		  return false;
		}
	  private boolean risolvi_lab(matrice m,int y,int x,int ye,int xe,int minmosse,Vector<casella> p,int mosse){
		  int r=m.getRows();
		  int c=m.getColumns();
		  if(fine_lab(y,x,ye,xe) && mosse<minmosse){
		    p.clear();
		    minmosse=mosse;
		    for(int i=0;i<r;i++)
		      for(int j=0;j<c;j++)
		        if(m.read(i,j)==2)
		          p.add(new casella(i,j));
		    return true;
		  }
		  if(bloccato(m,y,x) || m.read(y,x)==1)
		      return false;
		  for(int i=0;i<4;i++){
		    int k1=0,k2=0;
		    if(i==0){k1=1;k2=0;}
		    if(i==1){k1=0;k2=1;}
		    if(i==2){k1=-1;k2=0;}
		    if(i==3){k1=0;k2=-1;}
		    if(y+k1<r && x+k2<c && y+k1>=0 && x+k2>=0 && m.read(y+k1,x+k2)==0){
		      m.ref(y+k1,x+k2,2);
		      if(!risolvi_lab(m,y+k1,x+k2,ye,xe,minmosse,p,mosse+1) && !is_best_path(p,y+k1,x+k2))
		          m.ref(y+k1,x+k2,3);
		      else
		          m.ref(y+k1,x+k2,0);
		    }
		  }
		  return false;
		}
	  public matriceBinaria(int y,int x, int d) {
		  super(y,x,d);
		  aggiusta();
	  }
	  public matriceBinaria(matrice m) {
		  super(m);
		  aggiusta();
	  }
	  public matrice sum(matrice m) throws errSize{
		  if(!sizeEqual(m))
		    throw new errSize();
		  for(int i=0;i<getRows();i++)
		    for(int j=0;j<getColumns();j++){
		      ref(i,j,read(i,j)+m.read(i,j));
		    }
		  aggiusta();
		  return this;
	  }
	  public matrice sub(matrice m) throws errSize{
		  if(!sizeEqual(m))
		    throw new errSize();
		  for(int i=0;i<getRows();i++)
		    for(int j=0;j<getColumns();j++){
		      ref(i,j,read(i,j)-m.read(i,j));
		    }
		  aggiusta();
		  return this;
	  }
	  public matrice mul(matrice m) throws errSizeProd{
		  if(!(getColumns()==m.getRows() && getRows()==m.getColumns()))
		    throw new errSizeProd();
		  matriceBinaria ris=new matriceBinaria(getRows(),m.getColumns(),0);
		  for(int i=0;i<ris.getRows();i++)
		    for(int j=0;j<ris.getColumns();j++){
		      int somma=0;
		      for(int k=0;k<getColumns();k++)
		        somma+=read(i,k)*m.read(k,j);
		      if(somma>1)somma=1;
		      if(somma<0)somma=0;
		      ris.ref(i,j,somma);
		    }
		  r=ris.r;
		  c=ris.c;
		  pos=ris.pos;
		  return this;
	  }
	  public void writeAll(int n) {
		  super.writeAll(n);
		  aggiusta();
	  }
	  public void writeAllRandom() {
		  super.writeAllRandom(0,1);
	  }
	  public void reverse() {
		  for(int i=0;i<getRows();i++)
		    for(int j=0;j<getColumns();j++)
		      if(read(i,j)==1)ref(i,j,0);
		      else ref(i,j,1);
	  }
	  public matriceBinaria aggiusta() {
		  for(int i=0;i<getRows();i++)
		    for(int j=0;j<getColumns();j++){
		      if(read(i,j)<0)ref(i,j,0);
		      if(read(i,j)>1)ref(i,j,1);
		    }
		  return this;
	  }
	  public Vector<casella> labirinto(int ys,int xs,int ye,int xe) throws errLabirinto{
		  if(ye==0)ye=getRows()-1;
		  if(xe==0)xe=getColumns()-1;
		  Vector<casella> p=new Vector<casella>();
		  int minmosse=getColumns()*getRows()+1;
		  matrice m=new matrice(this);
		  risolvi_lab(m,ys,xs,ye,xe,minmosse,p,0);
		  if(p.isEmpty()){
		    throw new errLabirinto();
		  }
		  p.add(new casella(ys,xs));
		  return p;
		}
	};
