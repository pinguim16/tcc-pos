import { Component } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {AlertController} from "@ionic/angular";
import {Router} from "@angular/router";
import {Usuario} from "../../models/usuario";

@Component({
  selector: 'app-login',
  templateUrl: 'login.component.html',
  styleUrls: ['login.component.scss']
})
export class LoginComponent {

  usuario : Usuario;

  constructor(private http: HttpClient,
              public alertController: AlertController,
              private router : Router) {
    this.usuario = new Usuario();
    let existeUsu = localStorage.getItem("usuario");
    if(existeUsu){
      this.router.navigate(['/pesquisa']);
    }
  }

  async presentAlert(mensagem) {
    const alert = await this.alertController.create({
      header: 'Alerta',
      subHeader: 'Login',
      message: mensagem,
      buttons: ['OK']
    });
    await alert.present();
  }

  async login() {
    let mensagem;
    await this.http.post<Usuario>('http://localhost:8080/api/usuario/validaUsuario', this.usuario).subscribe(data=>{
      console.log(data);
      if(!data.error){
        this.router.navigate(['/pesquisa']);
        localStorage.setItem("usuario", "true");
      }else{
        this.presentAlert(data.mensagem);
      }
    });
  }
}
