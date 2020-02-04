import { NgModule } from '@angular/core';
import { PreloadAllModules, RouterModule, Routes } from '@angular/router';
import {LoginComponent} from "./login/login.component";
import {PesquisaComponent} from "./pesquisa/pesquisa.component";
import {CadastroComponent} from "./cadastro/cadastro.component";

const routes: Routes = [
  {
    path: '',
    component: LoginComponent
  },
  {
    path: 'pesquisa',
    component: PesquisaComponent
  },
  {
    path: 'cadastro',
    component: CadastroComponent
  }
];
@NgModule({
  imports: [
    RouterModule.forRoot(routes, { preloadingStrategy: PreloadAllModules })
  ],
  exports: [RouterModule]
})
export class AppRoutingModule {}
