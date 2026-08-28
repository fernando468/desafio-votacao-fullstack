import type { VotoRequest, VotoResponse } from "../types/voto.type";
import BaseService from "./base.service";

export default class VotoService extends BaseService<
  VotoRequest,
  VotoResponse
> {
  constructor() {
    super("/votos");
  }
}
