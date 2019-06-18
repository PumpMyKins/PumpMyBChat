package fr.pumpmybchat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.google.common.io.ByteStreams;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class ConfigManager {

	private Configuration configuration = null;
	private Main main;

	public ConfigManager(Main m) throws IOException {
		this.main = m;
		saveDefaultConfig();
		this.configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(getFile());
	}

	public void init() throws IOException{

			
			/*Main.host = this.configuration.getString("mysql.host");
			Main.port = this.configuration.getInt("mysql.port");
			Main.username = this.configuration.getString("mysql.username");
			Main.password = this.configuration.getString("mysql.password");
			Main.database = this.configuration.getString("mysql.database");*/
	}

	private final File getFile(){
		return new File(this.main.getDataFolder(), "config.yml");
	}

	private void saveDefaultConfig() throws IOException
	{
		if (!this.main.getDataFolder().exists()) {
			this.main.getDataFolder().mkdir();
			this.main.getLogger().info("Default configuration directory created !");
		}

		File file = getFile();
		if (!file.exists()) {

			file.createNewFile();
			InputStream is = this.main.getResourceAsStream("config.yml");

			OutputStream os = new FileOutputStream(file);

			ByteStreams.copy(is, os);
			os.close();
			is.close();


		}

	}

	public void save() throws IOException
	{
		ConfigurationProvider.getProvider(YamlConfiguration.class).save(this.configuration, getFile());
	}
}