package com.db.votacao;

import com.db.votacao.models.Associado;
import com.db.votacao.models.Pauta;
import com.db.votacao.models.Sessao;
import com.db.votacao.repositories.AssociadoRepository;
import com.db.votacao.repositories.PautaRepository;
import com.db.votacao.repositories.SessaoRepository;
import com.db.votacao.repositories.VotoRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@Profile("performance")
@Transactional
public class PerformanceDataSeeder implements CommandLineRunner {
    private final AssociadoRepository associadoRepository;
    private final PautaRepository pautaRepository;
    private final SessaoRepository sessaoRepository;
    private final VotoRepository votoRepository;
    private final EntityManager entityManager;

    public PerformanceDataSeeder(AssociadoRepository associadoRepository,
                                 PautaRepository pautaRepository,
                                 SessaoRepository sessaoRepository,
                                 VotoRepository votoRepository,
                                 EntityManager entityManager) {
        this.associadoRepository = associadoRepository;
        this.pautaRepository = pautaRepository;
        this.sessaoRepository = sessaoRepository;
        this.votoRepository = votoRepository;
        this.entityManager = entityManager;
    }

    @Transactional
    public void limparDados() {
        entityManager
                .createNativeQuery("""
                TRUNCATE TABLE voto, sessao, pauta, associado RESTART IDENTITY CASCADE
                """)
                .executeUpdate();
    }

    public String generate(long number) {
        String cpfBase = String.format("%09d", number);

        int firstDigit = calculateDigit(cpfBase);
        int secondDigit = calculateDigit(cpfBase + firstDigit);

        return cpfBase + firstDigit + secondDigit;
    }

    private int calculateDigit(String value) {
        int sum = 0;
        int weight = value.length() + 1;

        for (char digit : value.toCharArray()) {
            sum += Character.getNumericValue(digit) * weight--;
        }

        int remainder = sum % 11;

        return remainder < 2 ? 0 : 11 - remainder;
    }

    private Pauta criarPauta(String titulo, String descricao) {
        Pauta pauta = new Pauta();

        pauta.setTitulo(titulo);
        pauta.setDescricao(descricao);

        return pauta;
    }


    @Override
    public void run(String... args) throws Exception {
        limparDados();

        LocalDateTime agora = LocalDateTime.now();

        criarAssociados();
        if (pautaRepository.findById(1L).isEmpty()) {
            List<Pauta> pautas = criarPautas();
            List<Sessao> sessoes = pautas.stream()
                    .map(pauta -> criarSessao(pauta, agora))
                    .toList();
            sessaoRepository.saveAll(sessoes);
        }

    }

    private void criarAssociados() {
        int total = 10_000;

        List<Associado> associados = new ArrayList<>(total);

        for (int i = 1; i <= total; i++) {
            Associado associado = new Associado();
            associado.setCpf(generate(i));
            associados.add(associado);
        }

        associadoRepository.saveAll(associados);
    }

    private Sessao criarSessao(Pauta pauta, LocalDateTime dataInicio) {
        Sessao sessao = new Sessao();

        sessao.setPauta(pauta);
        sessao.setDataInicio(dataInicio);
        sessao.setDataFim(dataInicio.plusYears(30));

        return sessao;
    }

    private List<Pauta> criarPautas() {
        List<Pauta> pautas = List.of(
                criarPauta(
                        "Aprovacao do balanco",
                        "Aprovar o balanco financeiro anual da cooperativa."
                )
        );

        pautas = pautaRepository.saveAll(pautas);
        return pautas;
    }
}
