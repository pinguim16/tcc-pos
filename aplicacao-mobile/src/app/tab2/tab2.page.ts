import { Component } from '@angular/core';
import { Estado } from 'src/models/estado';
import { HttpClient } from '@angular/common/http';
import { Empresa } from 'src/models/empresa';
import { Observable } from 'rxjs';
import { Cidade } from 'src/models/cidade';

@Component({
  selector: 'app-tab2',
  templateUrl: 'tab2.page.html',
  styleUrls: ['tab2.page.scss']
})
export class Tab2Page {

  empresas: Empresa[] = [];
  estados: Estado[] = [];
  cidades: Cidade[] = [];

  constructor(private http: HttpClient) {
    this.getEstados();
  }

  async getEmpresas(idCidade) {
    await this.http.get<Empresa[]>('http://localhost:8080/api/empresas/'+idCidade).subscribe(data=>{
      this.empresas = data;
    });
  }

  async getEstados() {
    let teste = await this.http.get<Empresa[]>('http://localhost:8080/api/estados').subscribe(data=>{
      this.estados = data;
    });
  }

  async getCidades(idEstado) {
    let teste = await this.http.get<Empresa[]>('http://localhost:8080/api/cidades/' + idEstado).subscribe(data=>{
      this.cidades = data;
    });
  }

  buscaCidade(item){
    let estadoArray = this.estados.filter(est => est.nome === item.detail.value );
    let idEstado = estadoArray[0].id;
    this.getCidades(idEstado);
  }

  buscaEmpresas(item){
    let cidadeArray = this.cidades.filter(cid => cid.nome === item.detail.value );
    let idCidade = cidadeArray[0].id;
    this.getCidades(idCidade);
  }

}
