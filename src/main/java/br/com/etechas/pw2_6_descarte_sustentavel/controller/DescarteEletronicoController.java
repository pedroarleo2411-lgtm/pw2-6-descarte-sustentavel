package br.com.etechas.pw2_6_descarte_sustentavel.controller;

import br.com.etechas.pw2_6_descarte_sustentavel.entity.DescarteEletronico;
import br.com.etechas.pw2_6_descarte_sustentavel.repository.DescarteEletronicoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/descartes")
public class DescarteEletronicoController {

    @Autowired
    private DescarteEletronicoRepository repository;

    @GetMapping
    public ResponseEntity<List<DescarteEletronico>> listarTodos() {
        List<DescarteEletronico> descartes = repository.findAll();

        return ResponseEntity.ok(descartes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DescarteEletronico> buscarPorId(@PathVariable Long id) {
        Optional<DescarteEletronico> descarte = repository.findById(id);

        if (descarte.isPresent()) {
            return ResponseEntity.ok(descarte.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<DescarteEletronico> criar(
            @RequestBody DescarteEletronico descarte) {
        descarte.setPontosGerados(0);
        descarte.setValidado(false);

        DescarteEletronico novoDescarte = repository.save(descarte);

        return ResponseEntity.status(HttpStatus.CREATED).body(novoDescarte);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DescarteEletronico> atualizar(
            @PathVariable Long id,
            @RequestBody DescarteEletronico dados) {
        Optional<DescarteEletronico> resultado = repository.findById(id);

        if (resultado.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        DescarteEletronico descarte = resultado.get();
        descarte.setMatriculaAluno(dados.getMatriculaAluno());
        descarte.setTipoItem(dados.getTipoItem());
        descarte.setPesoGramas(dados.getPesoGramas());

        DescarteEletronico atualizado = repository.save(descarte);

        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        Optional<DescarteEletronico> resultado = repository.findById(id);

        if (resultado.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
    @PostMapping("/{id}/computar-pontos")
    public ResponseEntity<DescarteEletronico> computarPontos(
            @PathVariable Long id) {
        Optional<DescarteEletronico> resultado = repository.findById(id);

        if (resultado.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        DescarteEletronico descarte = resultado.get();

        if (descarte.getValidado()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        int fator = 1;

        if (descarte.getTipoItem().equals("BATERIA")) {
            fator = 5;
        } else if (descarte.getTipoItem().equals("PLACA")) {
            fator = 4;
        } else if (descarte.getTipoItem().equals("CELULAR_ANTIGO")) {
            fator = 10;
        } else {
            fator = 2;
        }
        descarte.setPontosGerados(
                (int) (descarte.getPesoGramas() / 100.0 * fator)
        );
        descarte.setValidado(true);
        repository.save(descarte);

        return ResponseEntity.ok(descarte);
    }
}