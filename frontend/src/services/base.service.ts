import type { PageRequest, PageResponse } from "../types/paginacao.type";
import api from "./api";

export default abstract class BaseService<Request, Response> {
  protected endpoint: string;

  constructor(endpoint: string) {
    this.endpoint = endpoint;
  }

  public async get(): Promise<Response> {
    const response = await api.get<Response>(this.endpoint);
    return response.data;
  }

  public async getById(id: string): Promise<Response> {
    const response = await api.get<Response>(`${this.endpoint}/${id}`);
    return response.data;
  }

  public async getPage(pageable: PageRequest): Promise<PageResponse<Response>> {
    const response = await api.get<PageResponse<Response>>(
      `${this.endpoint}/paginacao?pagina=${pageable.pagina}&tamanho=${pageable.tamanho}`,
    );
    return response.data;
  }

  public async post(data: Request): Promise<Response> {
    const response = await api.post<Response>(this.endpoint, data);
    return response.data;
  }

  public async put(id: string, data: Request): Promise<Response> {
    const response = await api.put<Response>(`${this.endpoint}/${id}`, data);
    return response.data;
  }

  public async delete(id: string): Promise<Response> {
    const response = await api.delete<Response>(`${this.endpoint}/${id}`);
    return response.data;
  }
}
