package kalk2;

import kalk2.errSize;
import kalk2.errSizeProd;
import kalk2.matrice;

import java.util.*;

class matrice{
	  int r=0;
	  int c=0;
	  int pos[];
	  public matrice(int y, int x, int d){
	    r=y;
	    c=x;
	    pos=new int[x*y];
	    for(int i=0;i<y*x;i++)
	      pos[i]=d;
	  }
	  public matrice(matrice m){
	    r=m.r;
	    c=m.c;
	    pos=new int[r*c];
	    for(int i=0;i<r*c;i++)
	      pos[i]=m.pos[i];
	  }
	  public int read(int y, int x){
	    return pos[y*c+x];
	  }
	  public int ref(int y, int x, int n){
	    pos[y*c+x]=n;
	    return pos[y*c+x];
	  }
	  public void stampa() {
		for(int i=0;i<r;i++){
		  for(int j=0;j<c;j++)
	        System.out.print(read(i,j)+" ");
		  System.out.print("\n");
	    }
	  }
	  public boolean sizeEqual(matrice m){
	    return c==m.c && r==m.r;
	  }
	  public matrice sum(matrice m) throws errSize{
	    if(!sizeEqual(m))
	      throw new errSize();
	    for(int i=0;i<r;i++)
	      for(int j=0;j<c;j++)
	        ref(i,j,read(i,j)+m.read(i,j));
	    return this;
	  }
	  public matrice sub(matrice m) throws errSize{
	    if(!sizeEqual(m))
	      throw new errSize();
	    for(int i=0;i<r;i++)
	      for(int j=0;j<c;j++)
	        ref(i,j,read(i,j)-m.read(i,j));
	    return this;
	  }
	  public matrice mul(matrice m) throws errSizeProd{
	    if(!(c==m.r && r==m.c))
	      throw new errSizeProd();
	    matrice ris= new matrice(r,m.c,0);
	    for(int i=0;i<ris.r;i++)
	      for(int j=0;j<ris.c;j++){
	        int somma=0;
	        for(int k=0;k<c;k++)
	          somma=somma+read(i,k)*m.read(k,j);
	          ris.ref(i,j,somma);
	        }
	    r=ris.r;
	    c=ris.c;
	    pos=ris.pos;
	    return this;
	  }
	  public void writeAll(int n){
	    for(int i=0;i<r;i++)
	      for(int j=0;j<c;j++)
	        ref(i,j,n);
	  }
	  public void writeAllRandom(int min, int max){
	    for(int i=0;i<r;i++)
	      for(int j=0;j<c;j++)
	        ref(i,j,(int)(Math.random()*1000)%(max+1-min)+min);
	  }
	  public void trasposta(){
	    matrice t=new matrice(this);
	    int temp=r;
	    r=c;
	    c=temp;
	    for(int i=0;i<r;i++)
	      for(int j=0;j<c;j++)
	        ref(i,j,t.read(j,i));
	  }
	  public void reverse(){
	    for(int i=0;i<r;i++)
		  for(int j=0;j<c;j++)
		    ref(i,j,-read(i,j));
	  }
	  public int getColumns(){
		return c;
	  }
	  public int getRows(){
		return r;  
	  }
	  public void swap(int y1, int x1, int y2, int x2, int sizey, int sizex){
		if(y1<0 || y1>=getRows() || x1<0 || x1>=getColumns() || y2<0 || y2>=getRows() || x2<0 || x2>=getRows() || sizey<0 || sizex<0)
	      return;
		else{
	      matrice temp=new matrice(this);
		  copy(temp,y1,x1,y2,x2,sizey,sizex);
		  copy(temp,y2,x2,y1,x1,sizey,sizex);
		}
	  }
	  public matrice copy(matrice t, int yd, int xd, int yt, int xt, int sizey, int sizex){
		if(yd<0 || yd>=getRows() || xd<0 || xd>=getColumns() || yt<0 || yt>=t.getRows() || xt<0 || xt>=t.getRows() || sizey<0 || sizex<0)
	      return this;
		if(sizey==0)sizey=t.getRows();
		if(sizex==0)sizex=t.getColumns();
	    for(int i=0;i+yd<getRows() && i<sizey;i++)
	      for(int j=0;j+xd<getColumns() && j<sizex;j++)
	        ref(yd+i,xd+j,t.read(yt+i,xt+j));
	    return this;
	  }
	  public int max(){
		int nMax=read(0,0);
		for(int i=0;i<r;i++)
		  for(int j=0;j<c;j++)
		    if(read(i,j)>nMax)
		      nMax=read(i,j);
		return nMax; 
	  }
	  public int min(){
		int nMin=read(0,0);
		for(int i=0;i<r;i++)
		  for(int j=0;j<c;j++)
		    if(read(i,j)<nMin)
		      nMin=read(i,j);
		return nMin;
	  }
	};
