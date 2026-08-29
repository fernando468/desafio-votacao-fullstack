import type { PautaRequest, PautaResponse } from "../types/pauta.type";
import BaseService from "./base.service";

export default class PautaService extends BaseService<
  PautaRequest,
  PautaResponse
> {
  constructor() {
    super("/pautas/v1");
  }
}
