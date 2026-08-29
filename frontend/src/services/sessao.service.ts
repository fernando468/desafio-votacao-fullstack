import type { SessaoRequest, SessaoResponse } from "../types/sessao.type";
import BaseService from "./base.service";

export default class SessaoService extends BaseService<
  SessaoRequest,
  SessaoResponse
> {
  constructor() {
    super("/sessoes/v1");
  }
}
