import {Component} from '@angular/core';
import {Estado} from 'src/models/estado';
import {HttpClient} from '@angular/common/http';
import {Empresa} from 'src/models/empresa';
import {Cidade} from 'src/models/cidade';
import {AlertController} from "@ionic/angular";
import {Usuario} from "../../models/usuario";
import {Router} from "@angular/router";

@Component({
  selector: 'app-cadastro',
  templateUrl: 'cadastro.component.html',
  styleUrls: ['cadastro.component.scss']
})
export class CadastroComponent {

  usuario: Usuario;

  //@PostMapping("/usuario/cadastrar")

  constructor(private http: HttpClient,
              public alertController: AlertController,
              private router : Router) {
    this.usuario = new Usuario();
  }

  async presentAlert() {
    const alert = await this.alertController.create({
      header: 'Alerta',
      subHeader: 'Cadastro Usuário',
      message: 'Cadastro Realizado com Sucesso !',
      buttons: ['OK']
    });
    await alert.present();
  }

  async cadastrarUsuario() {
    await this.http.post<Usuario>('http://localhost:8080/api/usuario/cadastrar', this.usuario).subscribe(data=>{
      this.usuario = data;
      console.log(data);
    });
    localStorage.setItem("usuario", "true");
    this.presentAlert();
    if(this.usuario.id){
      this.router.navigate(['']);
    }
  }

}
