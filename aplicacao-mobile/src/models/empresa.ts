import {Cidade} from './cidade';
import {CategoriaMaterial} from './categoria.material';
import {Material} from './material';

export class Empresa {

    id: number;
    nome: string;
    endereco: string;
    bairro: string;
    telefone: string;
    cep: string;
    cidade: Cidade;
    tipoEmpresa: string;
    categoriaMateriaisStr: string;
    materiaisStr: string;
}