import { Cidade } from './cidade';
import { Estado } from './estado';
import { CategoriaMaterial } from './categoria.material';
import { Material } from './material';

export class Empresa {

    id: number;
    nome: string;
    endereco: string;
    bairro: string;
    telefone: string;
    cep: string;
    cidade: Cidade;
    estado: Estado;
    tipoEmpresa:string;
    categoriaMaterial: CategoriaMaterial[] ;
    material:Material[];   

}