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

	Configuration configuration = null;

	public void init() throws Throwable {

		saveDefaultConfig();
		try {

			this.configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(getFile());
			Main.host = this.configuration.getString("mysql.host");
			Main.port = this.configuration.getInt("mysql.port");
			Main.username = this.configuration.getString("mysql.username");
			Main.password = this.configuration.getString("mysql.password");
			Main.database = this.configuration.getString("mysql.database");

		} catch(IOException e) {

			e.printStackTrace();
		}
	}
	
	public File getFile(){

		return new File(Main.getSharedInstance().getDataFolder(), "config.yml");
	}
	
	@SuppressWarnings("unused")
	private void saveDefaultConfig()
	{
		if (!Main.getSharedInstance().getDataFolder().exists()) {
			Main.getSharedInstance().getDataFolder().mkdir();
		}
		File file = getFile();
		if (!file.exists()) {
			try
			{
				file.createNewFile();
				InputStream is = Main.getSharedInstance().getResourceAsStream("config.yml");Throwable localThrowable6 = null;
				try
				{
					OutputStream os = new FileOutputStream(file);Throwable localThrowable7 = null;
					try
					{
						ByteStreams.copy(is, os);
						os.close();
						is.close();
					}
					catch (Throwable localThrowable1)
					{
						localThrowable7 = localThrowable1;throw localThrowable1;
					}
					finally {}
				}
				catch (Throwable localThrowable4)
				{
					localThrowable6 = localThrowable4;throw localThrowable4;
				}
				finally
				{
					if (is != null) {
						if (localThrowable6 != null) {
							try
							{
								is.close();
							}
							catch (Throwable localThrowable5)
							{
								localThrowable6.addSuppressed(localThrowable5);
							}
						} else {
							is.close();
						}
					}
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	public void save()
	{
		try
		{
			ConfigurationProvider.getProvider(YamlConfiguration.class).save(this.configuration, getFile());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}