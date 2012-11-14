package fr.mertzel.ose.controleur;

import java.awt.event.* ;

import fr.mertzel.ose.modele.Orientation;
import fr.mertzel.ose.modele.Parametres;
import fr.mertzel.ose.modele.PlanSalle;
import fr.mertzel.ose.modele.Position;
import fr.mertzel.ose.vue.FenetrePrincipale;

public class Controleur implements MouseListener, ActionListener {

	private FenetrePrincipale vue ;
	private PlanSalle modele ;
	private Position position ;
	
	public Controleur(FenetrePrincipale vue,PlanSalle modele) {
		this.vue = vue ;
		this.modele = modele ;
		vue.getItemOuvrir().addActionListener(this) ;
		vue.getItemEnregistrer().addActionListener(this) ;
		vue.getItemQuitter().addActionListener(this) ;
		vue.getItemPlacerNord().addActionListener(this) ;
		vue.getItemPlacerEst().addActionListener(this) ;
		vue.getItemPlacerSud().addActionListener(this) ;
		vue.getItemPlacerOuest().addActionListener(this) ;
		vue.getItemReorienterNord().addActionListener(this);
		vue.getItemReorienterEst().addActionListener(this);
		vue.getItemReorienterSud().addActionListener(this);
		vue.getItemReorienterOuest().addActionListener(this);
		vue.getItemRetirer().addActionListener(this) ;
		vue.getPlanSalle().addMouseListener(this) ;
	}
	
	public void actionPerformed(ActionEvent evt){
		Object source = evt.getSource() ;
		if(source == vue.getItemOuvrir()){
			System.out.println("Ouvrir") ;
		}
		else if(source == vue.getItemEnregistrer()){
			System.out.println("Enregistrer") ;
		}
		else if(source == vue.getItemQuitter()){
			System.out.println("Quitter") ;
			System.exit(0) ;
		}
		else if(source == vue.getItemPlacerNord()){
			modele.ajouterPoste(this.position,Orientation.NORD) ;
		}
		else if(source == vue.getItemPlacerEst()){
			modele.ajouterPoste(this.position,Orientation.EST) ;
		}
		else if(source == vue.getItemPlacerSud()){
			modele.ajouterPoste(this.position,Orientation.SUD) ;
		}
		else if(source == vue.getItemPlacerOuest()){
			modele.ajouterPoste(this.position,Orientation.OUEST) ;
		}
		else if(source == vue.getItemReorienterNord()){
			modele.retirerPoste(this.position);
			modele.ajouterPoste(this.position, Orientation.NORD);
		}
		else if(source == vue.getItemReorienterEst()){
			modele.retirerPoste(this.position);
			modele.ajouterPoste(this.position, Orientation.EST);
		}
		else if(source == vue.getItemReorienterSud()){
			modele.retirerPoste(this.position);
			modele.ajouterPoste(this.position, Orientation.SUD);
		}
		else if(source == vue.getItemReorienterOuest()){
			modele.retirerPoste(this.position);
			modele.ajouterPoste(this.position, Orientation.OUEST);
		}
		else if(source == vue.getItemRetirer()){
			modele.retirerPoste(this.position) ;
		}
		vue.visualiserPlan() ;
	}
	
	public void mouseClicked(MouseEvent evt){
	}
	
	public void mousePressed(MouseEvent evt){
		Object source = evt.getSource() ;
		if(source == vue.getPlanSalle()){
			if(evt.isPopupTrigger()){
				int rangee = evt.getY() / Parametres.HAUTEUR_RANGEE ;
				int travee = evt.getX() / Parametres.LARGEUR_TRAVEE ;
				this.position = new Position(rangee,travee) ;
				vue.afficherMenuContextuel(evt.getX(),evt.getY()) ;
			}
		}
	}
	
	public void mouseReleased(MouseEvent evt){
	}
	
	public void mouseEntered(MouseEvent evt){
	}
	
	public void mouseExited(MouseEvent evt){
	}
}

//YAHOOOOOOOO!
