package kalk2;

import kalk2.errQuadratoMagico;
import kalk2.errSize;
import kalk2.errSizeProd;
import kalk2.errSudoku;
import kalk2.errSudokuInit;
import kalk2.matrice;
import kalk2.matriceQuadrata;

class matriceQuadrata extends matrice{
	  private static boolean check_full(matriceQuadrata m) {
		  for(int i=0;i<m.getColumns();i++)
			    for(int j=0;j<m.getColumns();j++)
			      if(m.read(i,j)==0)
			        return false;
			  return true;
	  }
	  private static boolean check_sudoku(matriceQuadrata m) {
		  double dim=Math.sqrt(m.getColumns());
		  for(int i=0;i<m.getColumns();i++)
		    for(int j=0;j<m.getColumns();j++)
		      if(m.read(i,j)!=0)
		        for(int k=0;k<m.getColumns();k++){
		          if((m.read(i,j)==m.read(i,k) && k!=j) || (m.read(i,j)==m.read(k,j) && k!=i))
		            return false;
		          for(int h=0;h<m.getColumns();h++)
		            if(m.read(i,j)==m.read(k,h) && k/dim==i/dim && h/dim==j/dim && (k!=i && h!=j))
		              return false;
		      }
		  return true;
	  }
	  private static boolean risolvi_sudoku(matriceQuadrata m) {
		  if(check_sudoku(m) && check_full(m))
			    return true;
			  if(!check_sudoku(m))
			    return false;
			  for(int i=0;i<m.getColumns();i++)
			    for(int j=0;j<m.getColumns();j++)
			      if(m.read(i,j)==0)
			        for(int k=1;k<=m.getColumns();k++){
			          m.ref(i,j,k);
			          if(risolvi_sudoku(m))
			            return true;
			          m.ref(i,j,0);
			          if(m.read(i,j)==0 && k==m.getColumns())
			            return false;
			    }
			  return check_sudoku(m);
	  }
	  private static matriceQuadrata quadratoMagicoDispari(matriceQuadrata m) {
		  int dim=m.getColumns();
		  m.writeAll(0);
		  int x=(dim-1)/2,y=0;
		  for(int i=1;i<=dim*dim;i++){
		    m.ref(y,x,i);
		    int old_x=x,old_y=y;
		    y--;
		    if(y<0)y=dim-1;
		    x++;
		    if(x>=dim)x=0;
		    if(m.read(y,x)!=0){
		      x=old_x;
		      y=old_y+1;
		    }
		  }
		  return m;
	  }
	  private static matriceQuadrata quadratoMagicoPari(matriceQuadrata m) throws errSize,errSizeProd {
		  int hdim=m.getColumns()/2;
		  matriceQuadrata t=new matriceQuadrata(hdim,0);
		  matriceQuadrata a=new matriceQuadrata(quadratoMagicoDispari(t));
		  matriceQuadrata b=new matriceQuadrata(quadratoMagicoDispari(t));
		  b.sum(new matriceQuadrata(b.getColumns(),hdim*hdim));
		  matriceQuadrata c=new matriceQuadrata(quadratoMagicoDispari(t));
		  c.sum(new matriceQuadrata(c.getColumns(),hdim*hdim*2));
		  matriceQuadrata d=new matriceQuadrata(quadratoMagicoDispari(t));
		  d.sum(new matriceQuadrata(d.getColumns(),hdim*hdim*3));
		  m.copy(a,0,0,0,0,0,0);
		  m.copy(b,hdim,hdim,0,0,0,0);
		  m.copy(c,0,hdim,0,0,0,0);
		  m.copy(d,hdim,0,0,0,0,0);
		  int hhdim=(hdim-1)/2;
		  m.swap(0,0,hdim,0,hhdim,hhdim);
		  m.swap(hhdim,1,hhdim+hdim,1,1,hhdim*hhdim);
		  m.swap(hhdim+1,0,hhdim+hdim+1,0,hhdim,hhdim);
		  return m;
	  }
	  private static matriceQuadrata quadratoMagicoPariDiv4(matriceQuadrata m) {
		  int hhdim=m.getColumns()/4;
		  int dim=m.getColumns();
		  for(int i=0;i<m.getColumns();i++)
		    for(int j=0;j<m.getColumns();j++)
		      if((i<hhdim && (j<hhdim || j>=dim-hhdim)) || (i>=dim-hhdim && (j<hhdim || j>=dim-hhdim)) || (i>=hhdim && i<dim-hhdim && j>=hhdim && j<dim-hhdim))
		        m.ref(i,j,i*dim+j+1);
		      else
		        m.ref(i,j,dim*dim-(i*dim+j));
		  return m;
	  }
	  public matriceQuadrata(int x, int d) {
		  super(x,x,d);
	  }
	  public matriceQuadrata(matrice m) {
		  super(m);
	  }
	  public matriceQuadrata sudoku() throws errSudoku,errSudokuInit{
		  double drad=Math.sqrt(getColumns());
		  int rad=(int)drad;
		  if(rad<2){
		    throw new errSudoku();
		  }
		  if(rad*rad!=getColumns()){
		    throw new errSudoku();
		  }
		  for(int i=0;i<getColumns();i++)
		    for(int j=0;j<getColumns();j++)
		      if(read(i,j)<0 || read(i,j)>getColumns()){
		        throw new errSudokuInit();
		      }
		  matriceQuadrata m=new matriceQuadrata(this);
		  if(risolvi_sudoku(m)) {
			  r=m.r;
			  c=m.c;
			  pos=m.pos;
		  }
		  return this;
	  }
	  public matriceQuadrata quadratoMagico() throws errQuadratoMagico,errSize,errSizeProd{
		  if(getColumns()<3){
		    throw new errQuadratoMagico();
		  }
		  matriceQuadrata m=new matriceQuadrata(r,0);
		  if(getColumns()%2==1)
		    m=quadratoMagicoDispari(this);
		  else
		    if(getColumns()%4==0)
		      m=quadratoMagicoPariDiv4(this);
		    else
		      m=quadratoMagicoPari(this);
		  r=m.r;
		  c=m.c;
		  pos=m.pos;
		  return this;
	  }
	  public matriceQuadrata sum(matrice m) throws errSize{
	    if(!sizeEqual(m))
	      throw new errSize();
	    for(int i=0;i<r;i++)
	      for(int j=0;j<c;j++)
	        ref(i,j,read(i,j)+m.read(i,j));
	    return this;
	  }
	  public matriceQuadrata sub(matrice m) throws errSize{
	    if(!sizeEqual(m))
	      throw new errSize();
	    for(int i=0;i<r;i++)
	      for(int j=0;j<c;j++)
	        ref(i,j,read(i,j)-m.read(i,j));
	    return this;
	  }
	  public matriceQuadrata mul(matrice m) throws errSizeProd{
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
	};
