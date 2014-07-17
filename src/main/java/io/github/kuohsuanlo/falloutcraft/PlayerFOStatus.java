package io.github.kuohsuanlo.falloutcraft;

class PlayerFOStatus{
		protected int PlayerThirst;
		protected int PlayerFatigue;
		protected int PlayerRadiation;
		public PlayerFOStatus( String thirst, String fatigue, String radiation){
			PlayerThirst = Integer.valueOf(thirst);
			PlayerFatigue = Integer.valueOf(fatigue);
			PlayerRadiation = Integer.valueOf(radiation);
		}
		public void setThirst(int newThirst){
			PlayerThirst = newThirst;
		}
		public void setFatigue(int newFatigue){
			PlayerFatigue = newFatigue;
		}
		public void setRadiation(int newRadiation){
			PlayerRadiation = newRadiation;
		}
	}