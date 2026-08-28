import type {
  AssociadoRequest,
  AssociadoResponse,
} from "../types/associado.type";
import BaseService from "./base.service";

export default class AssociadoService extends BaseService<
  AssociadoRequest,
  AssociadoResponse
> {
  constructor() {
    super("/associados");
  }
}
