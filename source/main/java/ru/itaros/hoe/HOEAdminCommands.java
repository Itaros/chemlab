package ru.itaros.hoe;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;

public class HOEAdminCommands extends CommandBase {

	public HOEAdminCommands(){
		usage = ("Your server has HOE Core installed.\nThose commands require you to be server op:\n/hoe status - gets current HOE MTA status\n/hoe start [threads_count] - starts HOE MTA after being stopped\n/hoe stop - stops HOE MTA\n/hoe reboot - reboots HOE MTA\n/hoe recover - tries to start HOE MTA after crash".split("\n"));
	}
	
	@Override
	public String getCommandName() {
		return "hoe";
	}

	private String[] usage;
	
	@Override
	public String getCommandUsage(ICommandSender usr) {
		return "/hoe";
	}

	@Override
	public void processCommand(ICommandSender usr, String[] args) {
		if(usr instanceof EntityPlayerMP){
			EntityPlayer player = (EntityPlayerMP)usr;
			if(!MinecraftServer.getServer().getConfigurationManager().isPlayerOpped(usr.getCommandSenderName())){
				sendMessage(player,"You have no access to HOE MTA state machine!");
				return;
			}
			
			
			if(args.length==0){
				sendMessage(player,usage);
				return;
			}
			if(args.length>=1){
				String subcommand = args[0];
				
				if(subcommand.equals("status")){
					String status = HOE.HOETC().getState().toString();
					sendMessage(player,"HOE Status: "+status);
					return;
				}	
				
				if(subcommand.equals("recover")){
					sendMessage(player,"Restoring HOE MTA...");
					if(HOE.HOETC().recover()){
						sendMessage(player,"...Success!");
					}else{
						sendMessage(player,"...Incorrect state(No errors or stopped?)!");
					}
					return;
				}				
				if(subcommand.equals("stop")){
					sendMessage(player,"Shutting HOE MTA down...");
					if(HOE.HOETC().shutdown()){
						sendMessage(player,"...Success!");
					}else{
						sendMessage(player,"...Already stopped!");
					}
					return;
				}
				if(subcommand.equals("reboot")){
					sendMessage(player,"Rebooting HOE MTA...");
					if(HOE.HOETC().shutdown()){
						if(HOE.HOETC().startup()){
							sendMessage(player,"...Success!");
						}else{
							sendMessage(player,"...WARNING! CRITICAL ERROR! HOE MTA TC State is broken!");
						}
					}else{
						sendMessage(player,"...Already stopped!");
					}
					return;
				}				
				if(subcommand.equals("start")){
					if(args.length>=2){
						int hoeThreadsNumber=0;
						try{
							hoeThreadsNumber = Integer.parseInt(args[1]);
						}catch(NumberFormatException exp){
							sendMessage(player,"Failure! Wrong Arguments!");
							return;
						}
						sendMessage(player,"Powering HOE MTA up...");
						if(HOE.HOETC().startup(hoeThreadsNumber)){
							sendMessage(player,"...Success!");
						}else{
							sendMessage(player,"...Already started!");
						}
					}else{
						sendMessage(player,"Failure! You need to specify thread count for HOE MTA TC!");
					}
					return;
				}				
			}
		}
	}
	
	public static void sendMessage(EntityPlayer player, String[] text)
	{
		for(String s:text){
			sendMessage(player, s);
		}
	}
	public static void sendMessage(EntityPlayer player, String text)
	{
		player.addChatComponentMessage(new ChatComponentText(text));
	}

}
