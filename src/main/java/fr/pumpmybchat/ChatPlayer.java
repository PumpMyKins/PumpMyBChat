package fr.pumpmybchat;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ChatPlayer {

	/*private Map<UUID , Prefix> prefix;
	private Map<UUID , String> nickname;	

	public Map<UUID, Prefix> getPrefix() {
		return prefix;
	}
	public void setPrefix(Map<UUID, Prefix> prefix) {
		this.prefix = prefix;
	}
	public Map<UUID, String> getNickname() {
		return nickname;
	}
	public void setNickname(Map<UUID, String> nickname) {
		this.nickname = nickname;
	}

	public ChatPlayer() {

		this.prefix = new HashMap<UUID, Prefix>();
		this.nickname = new HashMap<UUID, String>();

		try {

			MySql mySQL = Main.getMySQL();
			ResultSet rs = mySQL.getResult(Main.REQUEST_GET_USER_PREFIX);

			while(rs.next()) {

					String pp = rs.getString("prefix");

					if(pp != null) {

						UUID playerUuid = UUID.fromString(rs.getString("uuid"));

						Prefix p = new Prefix();
						p.setActive(true);
						p.setModification(1);
						p.setPrefix(pp);
						p.setUuid(playerUuid);
						p.setWarn(rs.getInt("warn"));

						this.prefix.put(playerUuid, p);

					}
			}

		} catch(SQLException e) {

			e.printStackTrace();
		}
	}

	public void addNickname(UUID player, String nick) {

		if(this.nickname.containsKey(player))
			this.nickname.remove(player);

		this.nickname.put(player, nick);
	}

	public void removeNickname(UUID player) {

		if(this.nickname.containsKey(player))
			this.nickname.remove(player);

	}

	public boolean hasNickname(UUID player) {

		if(this.nickname.containsKey(player))
			return true;
		else {
			return false;

		}
	}

	public boolean changeActivation(UUID player) {

		if(this.hasPrefix(player)) {
			Prefix p = this.prefix.get(player);
			if(p != null) {
				if(p.isActive()) {
					p.setActive(false);
				} else {
					p.setActive(true);
				}
				this.prefix.remove(player);
				
				this.prefix.put(player, p);

				dbChangeActivation(player, p);

				return true;
			}
		}
		return false;
	}

	private void dbChangeActivation(UUID player, Prefix p) {

		try {
			Main.getMySQL().update("UPDATE `PrefixPlayer` SET `active`="+p.isActive()+" WHERE `uuid`= '"+player+"'");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public boolean resetPrefix(UUID player) {

		if(this.hasPrefix(player)) {
			Prefix p = this.prefix.get(player);
			if(p != null) {
				
				p.setPrefix("");
				
				this.prefix.remove(player);
				this.prefix.put(player, p);

				dbResetPrefix(player, p);

				return true;
			}
		}
		return false;
	}

	private void dbResetPrefix(UUID player, Prefix p) {

		try {
			Main.getMySQL().update("UPDATE `PrefixPlayer` SET `prefix`= '"+p.getPrefix()+"' WHERE `uuid`= '"+player+"'");
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	public boolean hasPrefix(UUID player) {
		
		if(!this.prefix.isEmpty()) {
			if(this.prefix.containsKey(player)) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	public boolean forceDelete(UUID player) {

		if(this.hasPrefix(player)) {
			
			this.prefix.remove(player);
			dbForceDelete(player);
			
			return true;
		}
			return false;
	}

	private void dbForceDelete(UUID player) {

		try {
			Main.getMySQL().update("DELETE FROM PrefixPlayer WHERE `uuid` = '"+player+"'");
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}
	
	public boolean warnPrefix(UUID player) {
		
		if(this.hasPrefix(player)) {
			
			Prefix p = this.getPrefix().get(player);
			int warn = p.getWarn() + 1;
			
			p.setWarn(warn);
			
			this.prefix.remove(player);
			this.prefix.put(player, p);
			
			dbWarnPrefix(player, p);
			return true;
		}
		return false;
	}
	
	private void dbWarnPrefix(UUID player, Prefix p) {
			
		try {
			Main.getMySQL().update("UPDATE `PrefixPlayer` SET `warn`="+p.getWarn()+" WHERE `uuid`= '"+player+"'");
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	
	public boolean setPrefix(UUID player, String pref) {
		
		if(this.hasPrefix(player)) {
						
			Prefix p = this.prefix.get(player);
			this.prefix.remove(player);
			p.setPrefix(pref);
			p.setModification(p.getModification()+1);
			
			this.prefix.put(player, p);
			
			dbSetPrefix(player, p, true);
			
			return true;
		} else {
						
			Prefix p = new Prefix();
			p.setActive(true);
			p.setModification(1);
			p.setPrefix(pref);
			p.setUuid(player);
			p.setWarn(0);
			
			this.prefix.put(player, p);
			
			dbSetPrefix(player, p, false);
			
			return true;
		}
	}
	
	private void dbSetPrefix(UUID player, Prefix p, boolean hadone) {
		
		if(hadone) {
			
			try {
				Main.getMySQL().update("UPDATE `PrefixPlayer` SET `prefix`= '"+p.getPrefix()+"',`modification`="+p.getModification()+" WHERE `uuid`= '"+player+"'");
			} catch (SQLException e) {
				
				e.printStackTrace();
			}

		} else {
			
			try {
				Main.getMySQL().update("INSERT INTO `PrefixPlayer`(`uuid`, `prefix`, `active`, `warn`, `modification`) VALUES ('"+p.getUuid()+"','"+p.getPrefix()+"',"+p.isActive()+","+p.getWarn()+","+p.getModification()+")");
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}
	}
	
	public void resetModification(UUID player) {
		
		if(this.hasPrefix(player)) {
			
			Prefix p = this.getPrefix().get(player);
			p.setModification(0);
			
			this.prefix.remove(player);
			this.prefix.put(player, p);
			
			dbResetModification(player);
		}
	}
	
	private void dbResetModification(UUID player) {
		
		try {
			Main.getMySQL().update("UPDATE `PrefixPlayer` SET `modification`="+0+" WHERE `uuid`= '"+player+"'");
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}*/
}
