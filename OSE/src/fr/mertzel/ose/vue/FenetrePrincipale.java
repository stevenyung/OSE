package fr.mertzel.ose.vue;

import java.awt.* ;
import javax.swing.* ;

import fr.mertzel.ose.modele.PlanSalle.Poste;
import fr.mertzel.ose.modele.Parametres;
import fr.mertzel.ose.modele.PlanSalle;
import fr.mertzel.ose.modele.Position;
import fr.mertzel.ose.modele.Orientation;

public class FenetrePrincipale extends JFrame {

	public class Plan extends JPanel {

		private static final long serialVersionUID = 1L;
		
		private PlanSalle modele ;
		
		public Plan(PlanSalle modele){
			super() ;
			this.modele = modele ;
			this.setBackground(Color.white) ;
		}
			
		public void paintComponent(Graphics g){
			super.paintComponent(g) ;
			Graphics2D g2d = (Graphics2D) g ;
			this.quadriller(g2d) ;
			this.placerPostes(g2d) ;
			this.tracerVisualisations(g2d) ;
		}
		
		private void quadriller(Graphics2D g){
			float dash[]={2f,0f,2f} ;
			BasicStroke pointilles = new BasicStroke(1,BasicStroke.CAP_BUTT,BasicStroke.JOIN_ROUND,1.0f,dash,2f) ;
			g.setStroke(pointilles) ;
			for(int i = 1 ; i <= Parametres.NB_TRAVEES-1 ; i++){
				g.drawLine(Parametres.LARGEUR_TRAVEE*i,0,Parametres.LARGEUR_TRAVEE*i,Parametres.NB_RANGEES*Parametres.HAUTEUR_RANGEE-1) ;
			}
			for(int i = 1 ; i <= Parametres.NB_RANGEES-1 ; i++){
				g.drawLine(0,Parametres.HAUTEUR_RANGEE*i,Parametres.NB_TRAVEES*Parametres.LARGEUR_TRAVEE-1,Parametres.HAUTEUR_RANGEE*i) ;
			}
		}
		
		private void placerPostes(Graphics2D g){
			g.setStroke(new BasicStroke()) ;
			for(Poste poste : modele){
				g.setColor(Color.gray) ;
				int xPoste = Parametres.posteX(poste.getPosition().getTravee(),poste.getOrientation()) ;
				int yPoste = Parametres.posteY(poste.getPosition().getRangee(),poste.getOrientation()) ;
				int xPersonne = Parametres.personneX(poste.getPosition().getTravee(),poste.getOrientation()) ;
				int yPersonne = Parametres.personneY(poste.getPosition().getRangee(),poste.getOrientation()) ;
				if(poste.getOrientation() == Orientation.NORD || poste.getOrientation() == Orientation.SUD){
					g.fill3DRect(xPoste,yPoste,Parametres.LONGUEUR_POSTE,Parametres.LARGEUR_POSTE,true) ;
				}
				else {
					g.fill3DRect(xPoste,yPoste,Parametres.LARGEUR_POSTE,Parametres.LONGUEUR_POSTE,true) ;
				}
				g.drawOval(xPersonne,yPersonne,Parametres.LARGEUR_PERSONNE,Parametres.LARGEUR_PERSONNE) ;
				if(poste.peutVoir()){
						g.setColor(Color.red) ;
				}
				else {
					g.setColor(Color.blue) ;
				}
				g.fillOval(xPersonne,yPersonne,20,20) ;
			}
		}
		
		private void tracerVisualisations(Graphics2D g){
			float dash[]={2f,0f,2f} ;
			BasicStroke pointilles = new BasicStroke(1,BasicStroke.CAP_BUTT,BasicStroke.JOIN_ROUND,1.0f,dash,2f) ;
			g.setStroke(pointilles) ;
			g.setColor(Color.red) ;
			for(Poste poste : modele){
				int centreX = Parametres.centrePersonneX(poste.getPosition().getTravee(),poste.getOrientation()) ;
				int centreY = Parametres.centrePersonneY(poste.getPosition().getRangee(),poste.getOrientation()) ;
				for(Poste posteVisible : poste.getPostesVisibles()){
					int visibleX = Parametres.centrePositionX(posteVisible.getPosition().getTravee()) ;
					int visibleY = Parametres.centrePositionY(posteVisible.getPosition().getRangee()) ;
					g.drawLine(centreX,centreY,visibleX,visibleY) ;
				}
			}
		}
	}

	private static final long serialVersionUID = 1L;

	private PlanSalle modele ;
	private Plan planSalle ;
	
	private JMenuItem itemNouveau ;
	private JMenuItem itemOuvrir ;
	private JMenuItem itemEnregistrer ;
	private JMenuItem itemQuitter ;
	
	private JPopupMenu menuActions ;
	
	private JMenu menuPlacer ;
	private JMenu menuReorienter ;
	private JMenuItem itemPlacerNord ;
	private JMenuItem itemPlacerEst ;
	private JMenuItem itemPlacerSud ;
	private JMenuItem itemPlacerOuest ;
	private JMenuItem itemReorienterNord ;
	private JMenuItem itemReorienterEst ;
	private JMenuItem itemReorienterSud ;
	private JMenuItem itemReorienterOuest ;
	private JMenuItem itemRetirer ;

	public FenetrePrincipale(PlanSalle modele){
		super() ;
		this.modele = modele ;
		this.setTitle(modele.getNom() + " - OSE") ;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ;
		this.creerBarreMenus() ;
		this.creerMenuContextuel() ;
		JLayeredPane lp = new JLayeredPane() ;
		lp.setPreferredSize(new Dimension(Parametres.NB_TRAVEES*Parametres.LARGEUR_TRAVEE,Parametres.NB_RANGEES*Parametres.HAUTEUR_RANGEE)) ;
		planSalle = new Plan(modele) ;
		planSalle.setBounds(0,0,Parametres.NB_TRAVEES*Parametres.LARGEUR_TRAVEE,Parametres.NB_RANGEES*Parametres.HAUTEUR_RANGEE) ;
		lp.add(planSalle,new Integer(0)) ;
		Container conteneur = this.getContentPane() ;
		conteneur.setLayout(new FlowLayout()) ;
	
		conteneur.add(lp) ;
		this.pack() ;
		this.setLocationRelativeTo(null) ;
		this.setVisible(true) ;
	}
	
	private void creerBarreMenus(){
		JMenuBar barreMenus = new JMenuBar() ;
		
		JMenu menuFichier = new JMenu("Fichier") ;
		itemNouveau = new JMenuItem("Nouveau") ;
		itemOuvrir = new JMenuItem("Ouvrir") ;
		itemEnregistrer = new JMenuItem("Enregistrer") ;
		itemQuitter = new JMenuItem("Quitter") ;
		menuFichier.add(itemNouveau) ;
		menuFichier.add(itemOuvrir) ;
		menuFichier.add(itemEnregistrer) ;
		menuFichier.addSeparator() ;
		menuFichier.add(itemQuitter) ;
		barreMenus.add(menuFichier) ;
		
		this.setJMenuBar(barreMenus) ;
		itemNouveau.setEnabled(false) ;
		itemOuvrir.setEnabled(false) ;
		itemEnregistrer.setEnabled(false) ;
	}
	
	private void creerMenuContextuel(){
		menuActions = new JPopupMenu() ;
		menuPlacer = new JMenu("Placer") ;
		menuActions.add(menuPlacer) ;
		menuReorienter = new JMenu("Réorienter");
		menuActions.add(menuReorienter);
		itemRetirer = new JMenuItem("Retirer") ;
		menuActions.add(itemRetirer) ;
		itemPlacerNord = new JMenuItem("vers Nord") ;
		menuPlacer.add(itemPlacerNord) ;
		itemPlacerEst = new JMenuItem("vers Est") ;
		menuPlacer.add(itemPlacerEst) ;
		itemPlacerSud = new JMenuItem("vers Sud") ;
		menuPlacer.add(itemPlacerSud) ;
		itemPlacerOuest = new JMenuItem("vers Ouest") ;
		menuPlacer.add(itemPlacerOuest) ;
		itemReorienterNord = new JMenuItem("vers Nord") ;
		menuReorienter.add(itemReorienterNord) ;
		itemReorienterEst = new JMenuItem("vers Est") ;
		menuReorienter.add(itemReorienterEst) ;
		itemReorienterSud = new JMenuItem("vers Sud") ;
		menuReorienter.add(itemReorienterSud) ;
		itemReorienterOuest = new JMenuItem("vers Ouest") ;
		menuReorienter.add(itemReorienterOuest) ;
	}
	
	public JMenuItem getItemOuvrir(){
		return this.itemOuvrir ;
	}
	
	public JMenuItem getItemEnregistrer(){
		return this.itemEnregistrer ;
	}
	
	public JMenuItem getItemQuitter(){
		return this.itemQuitter ;
	}
	
	public JMenuItem getItemPlacerNord(){
		return this.itemPlacerNord ;
	}
	
	public JMenuItem getItemPlacerEst(){
		return this.itemPlacerEst ;
	}
	
	public JMenuItem getItemPlacerSud(){
		return this.itemPlacerSud ;
	}
	
	public JMenuItem getItemPlacerOuest(){
		return this.itemPlacerOuest ;
	}
	
	public JMenuItem getItemReorienterNord(){
		return this.itemReorienterNord;
	}
	
	public JMenuItem getItemReorienterEst(){
		return this.itemReorienterEst;
	}
	
	public JMenuItem getItemReorienterSud(){
		return this.itemReorienterSud;
	}
	
	public JMenuItem getItemReorienterOuest(){
		return this.itemReorienterOuest;
	}
	
	public JMenuItem getItemRetirer(){
		return this.itemRetirer ;
	}

	public Plan getPlanSalle(){
		return this.planSalle ;
	}
	
	public void visualiserPlan(){
		this.planSalle.repaint() ;
	}
	
	public void afficherMenuContextuel(int x,int y){
		Position position = new Position(y/Parametres.HAUTEUR_RANGEE,x/Parametres.LARGEUR_TRAVEE) ;
		if(this.modele.positionOccupee(position)){
			this.menuPlacer.setEnabled(false) ;
			this.menuReorienter.setEnabled(true);
			this.itemRetirer.setEnabled(true) ;
		}
		else {
			this.menuPlacer.setEnabled(true) ;
			this.menuReorienter.setEnabled(false);
			this.itemRetirer.setEnabled(false) ;
		}
		menuActions.show((Component)planSalle,x,y) ;
	}
}