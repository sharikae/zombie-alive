/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.ac.teu.zombiealive.objects;

import com.sun.java.swing.plaf.windows.WindowsBorders;
import jp.ac.teu.zombiealive.util.Console;

/**
 *
 * @author C0116125
 */
public class Battle {
    private static PlayerCharacter pc;
    private static DaughterCharacter dt;
    Manager mg=new Manager();
    private static boolean test=false;//テスト用か(trueでテスト用)
    
    private static boolean finished=true;//終了したら
    private static boolean vs=true;//戦闘に敗北したらfalseになる,
    
    public Battle(PlayerCharacter p){//初期化
        Battle.pc=p;//ここいる？
    }

    public static PlayerCharacter getPc() {//戦闘後の初期化
        return pc;
    }

    public static DaughterCharacter getDt() {
        return dt;
    }
    
    
    
    public static boolean vsZombie(PlayerCharacter p){
        System.out.println("ゾンビがあらわれた！");
        finished=true;//終了したら
        vs=true;//戦闘に敗北したらfalseになる,
        ZombieCharacter zc=new ZombieCharacter();
        pc=p;
        while (finished) {
            //int[] a=pc.getAttackDamage();//とりあえず帰ってくるのは[]ではない場合の処理
            int a=2;
            zc.damageToZombie(a);
            System.out.println("あなたは"+a+"ダメージを与えた");
            if (zc.getHp()<=0) {//ゾンビが0ならば
                System.out.println("敵を倒した！");
                System.out.println("Please Any Key");
                Console.read();
                break;//勝利は初期化されてるので戻る
            }
            System.out.println("あなたは1ダメージを喰らった!");
            pc.setHp(pc.getHp()-1);//１ダメージ喰らう
            if (pc.getHp()<=0) {
                System.out.println("あなたは死んだ！");
                vs=false;
                break;
            }
            if (test) {
                Console.write("現在の体力状態: " + pc.getHp() + "\nPlease Any Key");
            }else{
                Console.write("現在の体力状態: " + pc.getAboutHp()+ "\nPlease Any Key");
            }
            Console.read();
            
        }
        return vs;
    }
    
    public static boolean vsZombie(PlayerCharacter p,int i){
        finished=true;//終了したら
        vs=true;//戦闘に敗北したらfalseになる,
        ZombieCharacter zc1=new ZombieCharacter();
        ZombieCharacter zc2=new ZombieCharacter();
        ZombieCharacter zc3=new ZombieCharacter();
        int n=i;//書き換えを行うので一応やっとく処理
        int x=0;
        switch (n){
            case 2://２体なら
                zc3.damageToZombie(99);//ゾンビ3はいない
            case 1://１体なら
                zc2.damageToZombie(99);//ゾンビ2もいない
        }
        while (finished) {
            int[] a=pc.getAttackDamage();//とりあえず帰ってくるのは[]ではない場合の処理
            if (a[1]==0) {//全体攻撃じゃなければ
                if (zc3.getHp() > 0) {//ゾンビ３が死んでなければ
                    zc3.damageToZombie(a[0]);
                    x=3;
                } else if (zc2.getHp() > 0) {//ゾンビ2が死んでなければ
                    zc2.damageToZombie(a[0]);
                    x=2;
                } else if (zc1.getHp() > 0) {//ゾンビ1が死んでなければ
                    zc1.damageToZombie(a[0]);
                    x=1;
                }
                System.out.println("あなたはゾンビ"+x+"に"+a[0]+"ダメージを与えた");
                
            }else{//全体攻撃ならば
                zc3.damageToZombie(a[0]);
                zc2.damageToZombie(a[0]);
                zc1.damageToZombie(a[0]);
                
                System.out.println("あなたは"+n+"体に"+a[0]+"ダメージ与えた！");
                
            }
            
            
            if (zc1.getHp()<=0&&zc2.getHp()<=0&&zc3.getHp()<=0) {//ゾンビが0ならば
                System.out.println("全ての敵を倒した！\n"
                        + "please any key");
                Console.read();
                break;//勝利は初期化されてるので戻る
            }
            n=0;//生存確認
            if (zc1.getHp()>0)  n++;//ゾンビ1の生存確認
            if(zc2.getHp()>0)   n++;//ゾンビ2の生存確認
            if(zc3.getHp()>0)   n++;//ゾンビ3の生存確認
            
            System.out.println("あなたは"+n+"ダメージを喰らった!");
            pc.setHp(pc.getHp()-n);
            if (pc.getHp()<=0) {
                System.out.println("あなたは死んだ！");
                vs=false;
                break;
            }
            if (test) {
                Console.write("現在の体力状態: " + pc.getHp() + "\nPlease Any Key");
            }else{
                Console.write("現在の体力状態: " + pc.getAboutHp()+ "\nPlease Any Key");
            }
            Console.read();
        }
        return vs;
    }
    
    public static boolean vsBoss(PlayerCharacter p ,DaughterCharacter d){
        BossCharacter bc=new BossCharacter();
        dt=d;
        pc=p;//初期化
        int turn=0;
        vs=true;
        int a,b;
        
        System.out.println("ボスが立ちはだかった！");
        
        while (finished) {
            System.out.println("現在の状態:"+pc.getAboutHp());
            a=pc.getAttackDamage()[0];
            System.out.println("あなたは"+a+"ダメージを与えた！");
            bc.damagedHitPoint(a);//ダメージ処理
            if (bc.getBossHitPoint()<=0) {//ボスが死んだら
                System.out.println("ボスを倒した！");
                break;
            }
            b=bc.getBossAttack();

            System.out.println("ボスからの攻撃で"+b+"ダメージを喰らった！");
            pc.setHp(pc.getHp()-b);
            if(pc.getHp()<=0){
                System.out.println("あなたはしんだ！");
                vs=false;
                break;
            }//死んだら
            
            if(dt.get_daughterPosition()==6&&dt.daughter_possible_action){//部屋に入っているかつ移動可能ならば
                //ここに娘出現時の処理を行う。
                vs=Battle.vsDauter(pc, dt);
                dt=Battle.getDt();
                pc=Battle.getPc();
            }
            
            dt.turn_update();
            turn++;
        }
        if (test) {
            Console.write("現在の体力状態: " + pc.getHp() + "\nPlease Any Key");
        }else{
            Console.write("現在の体力状態: " + pc.getAboutHp()+ "\nPlease Any Key");
        }
        System.out.println("Please Any Key");
        Console.read();
        return vs;
    }
    
    public static boolean vsDauter(PlayerCharacter p,DaughterCharacter d){
        dt=d;
        pc=p;//初期化
        vs=true;
        int a;
        System.out.println("娘が出現した娘の攻撃で5ダメージを喰らった");
        pc.setHp(pc.getHp()-5);
        if(pc.getHp()<=0){
            System.out.println("YouDead！");
            vs=false;
        }//死んだら
        a=pc.getAttackDamage()[0];
        System.out.println("あなたの反撃\n娘に"+a+"ダメージを与えてしまった");
        dt.battle_daughter(a);
        if (dt.get_hp()<=0) {//娘死んだら
            System.out.println("娘を殺してしまった！");
            vs=false;
            
        }
        System.out.println("Please Any Key");
       Console.read();
        return vs;
    } 
    
}
