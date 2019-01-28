package remix.myplayer.util;

/**
 * Created by taeja on 16-2-17.
 */

/**
 * 常量值
 */
public class Constants {

  //操作类型
  public final static int SONG = 0;
  public final static int ALBUM = 1;
  public final static int ARTIST = 2;
  public final static int FOLDER = 3;
  public final static int PLAYLIST = 4;
  public final static int PLAYLISTSONG = 5;

  public final static String EXIT = "remix.music.EXIT";
  public final static String SOUNDEFFECT_ACTION = "remix.music.SOUNDEFFECT_ACTION";

  //播放模式
  public final static int PLAY_LOOP = 1;
  public final static int PLAY_SHUFFLE = 2;
  public final static int PLAY_REPEAT = 3;


  //0:软件锁屏 1:系统锁屏 2:关闭
  public final static int APLAYER_LOCKSCREEN = 0;
  public final static int SYSTEM_LOCKSCREEN = 1;
  public final static int CLOSE_LOCKSCREEN = 2;

}
