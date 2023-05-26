import clang.cindex

class StaticFieldVisitor:
    def __init__(self):
        self.static_fields = []

    def visit_field_decl(self, node):
        # Check if the field is static
        # if node.is_static():

        # Extract the name and type of the field
        name = node.spelling
        type = node.type.spelling

        # Add the static field to the list
        self.static_fields.append((name, type))

def extract_static_fields(source_file):
    # Set up the Clang index
    index = clang.cindex.Index.create()

    # Parse the source code and create an AST
    translation_unit = index.parse(source_file)

    # Traverse the AST with the visitor
    visitor = StaticFieldVisitor()
    for node in translation_unit.cursor.walk_preorder():
        if node.kind == clang.cindex.CursorKind.VAR_DECL and node.is_definition():
            if node.semantic_parent.kind == clang.cindex.CursorKind.CLASS_DECL:
                visitor.visit_field_decl(node)

    count = len(visitor.static_fields)

    # Return the list of static fields
    return visitor.static_fields, count
