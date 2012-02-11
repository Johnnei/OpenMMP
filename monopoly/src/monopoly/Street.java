package monopoly;

public enum Street
{

	Station, Voorziening, Start, Kans, AlgemeenFonds, VrijParkeren, Tax, Tax_Extra, Jail, ToJail, Ons_Dorp, Arnhem, Haarlem, Utrecht, Groningen, Gravenhaven, Rotterdam, Amsterdam;
	
	public static String toString(Street s) {
		if(s == Station)
			return "Stations";
		if(s == Voorziening)
			return "Voorzieningen";
		if(s == Start)
			return "Start";
		if(s == Kans)
			return "Chance";
		if(s == AlgemeenFonds)
			return "General";
		if(s == VrijParkeren)
			return "Free Parking";
		if(s == Tax || s == Tax_Extra)
			return "Tax";
		if(s == Jail || s == ToJail)
			return "Jail";
		if(s == Ons_Dorp)
			return "Ons Dorp";
		if(s == Arnhem)
			return "Arnhem";
		if(s == Haarlem)
			return "Haarlem";
		if(s == Utrecht)
			return "Utrecht";
		if(s == Groningen)
			return "Groningen";
		if(s == Gravenhaven)
			return "'S Gravenhagen";
		if(s == Rotterdam)
			return "Rotterdam";
		if(s == Amsterdam)
			return "Amsterdam";
		return "invalid";
	}

}
