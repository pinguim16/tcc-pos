import {Component} from '@angular/core';
import {Estado} from 'src/models/estado';
import {HttpClient} from '@angular/common/http';
import {Empresa} from 'src/models/empresa';
import {Cidade} from 'src/models/cidade';
import {AlertController} from "@ionic/angular";

@Component({
  selector: 'app-cadastro',
  templateUrl: 'cadastro.component.html',
  styleUrls: ['cadastro.component.scss']
})
export class CadastroComponent {

  empresas: Empresa[] = [];
  estados: Estado[] = [];
  cidades: Cidade[] = [];
  cidadeId: number;
  estadoId: any;

  constructor(private http: HttpClient, public alertController: AlertController) {
    this.getEstados();
  }

  async presentAlert() {
    const alert = await this.alertController.create({
      header: 'Alerta',
      subHeader: 'Limpar pesquisa',
      message: 'Pesquisa já se encontra limpa !',
      buttons: ['OK']
    });
    await alert.present();
  }

  async getEmpresas(idCidade) {
    await this.http.get<Empresa[]>('http://localhost:8080/api/empresas/'+idCidade).subscribe(data=>{
      this.empresas = data;
      console.log(data);
    });
  }

  async getEstados() {
    await this.http.get<Empresa[]>('http://localhost:8080/api/estados').subscribe(data=>{
      this.estados = data;
    });
  }

  async getCidades(idEstado) {
    await this.http.get<Cidade[]>('http://localhost:8080/api/cidades/' + idEstado).subscribe(data=>{
      this.cidades = data;
    });
  }

  buscaCidade(item){
    if(item.detail.value) {
      let estadoArray = this.estados.filter(est => est.nome === item.detail.value);
      let idEstado = estadoArray[0].id;
      this.getCidades(idEstado);
    }
  }

  buscaEmpresas(){
    this.getEmpresas(this.cidadeId);
  }

  limparPesquisa(){
    if(this.estadoId && this.cidadeId) {
      this.empresas = [];
      this.cidadeId = null;
      this.estadoId = null;
      this.getEstados();
    }else {
      this.presentAlert();
    }
  }
}
