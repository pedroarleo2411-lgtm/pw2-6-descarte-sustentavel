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

    // GET - Listar todos os descartes
    @GetMapping
    public ResponseEntity<List<DescarteEletronico>> listarTodos() {
        List<DescarteEletronico> descartes = repository.findAll();

        return ResponseEntity.ok(descartes);
    }

    // GET - Buscar descarte por ID
    @GetMapping("/{id}")
    public ResponseEntity<DescarteEletronico> buscarPorId(@PathVariable Long id) {
        Optional<DescarteEletronico> descarte = repository.findById(id);

        if (descarte.isPresent()) {
            return ResponseEntity.ok(descarte.get());
        }

        return ResponseEntity.notFound().build();
    }

    // POST - Criar novo descarte
    @PostMapping
    public ResponseEntity<DescarteEletronico> criar(
            @RequestBody DescarteEletronico descarte) {

        DescarteEletronico novoDescarte = repository.save(descarte);

        return ResponseEntity.status(HttpStatus.CREATED).body(novoDescarte);
    }

    // DELETE - Excluir descarte por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}